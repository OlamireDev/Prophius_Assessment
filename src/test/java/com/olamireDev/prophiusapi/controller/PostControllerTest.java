package com.olamireDev.prophiusapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olamireDev.prophiusapi.ProphiusApiApplication;
import com.olamireDev.prophiusapi.payload.request.CreatePostDTO;
import com.olamireDev.prophiusapi.payload.request.EditPostDTO;
import com.olamireDev.prophiusapi.payload.request.LoginDTO;
import com.olamireDev.prophiusapi.payload.response.CreatedUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProphiusApiApplication.class) // Specify your Spring Boot application class
@AutoConfigureMockMvc
class PostControllerTest {

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
    void createPost() throws Exception {
        var createPostDTO = new CreatePostDTO("test post", "test post body");
        var resultActions = mockMvc.perform(post("/api/v1/posts/create" )
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getPost() throws Exception {
        mockMvc.perform(get("/api/v1/posts/1" )
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts/all/1" )
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void updatePost() throws Exception {
        var editPostDTO = new EditPostDTO(1L, "test", "content");
        var resultActions = mockMvc.perform(put("/api/v1/posts/update" )
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editPostDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePost() throws Exception {
        mockMvc.perform(delete("/api/v1/posts/delete/4" )
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }
}