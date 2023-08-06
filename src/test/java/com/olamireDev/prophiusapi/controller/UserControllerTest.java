package com.olamireDev.prophiusapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olamireDev.prophiusapi.ProphiusApiApplication;
import com.olamireDev.prophiusapi.payload.request.CreateUserDTO;
import com.olamireDev.prophiusapi.payload.request.EditUserDTO;
import com.olamireDev.prophiusapi.payload.request.LoginDTO;
import com.olamireDev.prophiusapi.payload.response.CreatedUserDTO;
import com.olamireDev.prophiusapi.repository.UserRepository;
import com.olamireDev.prophiusapi.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProphiusApiApplication.class) // Specify your Spring Boot application class
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;


    @BeforeEach
    public void getToken() throws Exception {
        var loginDTO = new LoginDTO("test1@gmail.com","hope");
        var resultActions = mockMvc.perform(get("/api/v1/user/login" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
        var userDTO = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), CreatedUserDTO.class);
        token = userDTO.getToken();
    }

    @Test
    public void createUserValidDetails() throws Exception {
        var createUserDTO = new CreateUserDTO("John Wick", "test5@gmail.com", "hope", "http://link.com/zelda.jpg");
        var resultActions = mockMvc.perform(post("/api/v1/user/create" )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk());
        var userDTO = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), CreatedUserDTO.class);
        assertNotNull(userDTO.getId());
        assertEquals("John Wick", userDTO.getUserName());
    }

    @Test
    public void createUserExistingEmailException() throws Exception {
        var createUserDTO = new CreateUserDTO("John Wick", "test1@gmail.com", "hope", "http://link.com/zelda.jpg");
        mockMvc.perform(post("/api/v1/user/create" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void loginValidDetail() throws Exception {
        var loginDTO = new LoginDTO("test1@gmail.com","hope");
        mockMvc.perform(get("/api/v1/user/login" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginInvalidDetails() throws Exception {
        var loginDTO = new LoginDTO("test11@gmail.com","hope");
        mockMvc.perform(get("/api/v1/user/login" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
        loginDTO = new LoginDTO("test1@gmail.com","hoped");
        mockMvc.perform(get("/api/v1/user/login" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void  editUserValidDetails() throws Exception {
        var edituserDTO = new EditUserDTO("John McClaine", "hoped", "http://peach.com/mario.jpg");
        var resultActions = mockMvc.perform(put("/api/v1/user/editUser")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(edituserDTO)))
                .andExpect(status().isOk());
        var userDTO = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("Successfully changed Username, Password, Profile picture", userDTO);
    }

    @Test
    public void  editUserNoAuth() throws Exception {
        var editUserDTO = new EditUserDTO("John McClaine", "hoped", "http://peach.com/mario.jpg");
        mockMvc.perform(put("/api/v1/user/editUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editUserDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void  followUserValidRequest() throws Exception {
        var resultActions = mockMvc.perform(put("/api/v1/user/follow/{id}", 2)
                        .header("Authorization", token))
                        .andExpect(status().isOk());
        var response  = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("Successfully followed user", response);
    }

    @Test
    public void  followUserInvalidRequest() throws Exception {
        var resultActions = mockMvc.perform(put("/api/v1/user/follow/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());
        var response = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("You cannot follow/unfollow yourself", response);
    }

    @Test
    public void  unfollowUserValidRequest() throws Exception {
        var resultActions = mockMvc.perform(put("/api/v1/user/unfollow/{id}", 2)
                        .header("Authorization", token))
                .andExpect(status().isOk());
        var response  = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("Successfully unfollowed user", response);
    }

    @Test
    public void  unfollowUserInvalidRequest() throws Exception {
        var resultActions = mockMvc.perform(put("/api/v1/user/unfollow/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());
        var response = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("You cannot follow/unfollow yourself", response);
    }

    @Test
    public void  deleteUser() throws Exception {
        var resultActions = mockMvc.perform(delete("/api/v1/user/delete")
                        .header("Authorization", token))
                .andExpect(status().isOk());
        var response = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(response.startsWith("Successfully deleted "));
    }

}