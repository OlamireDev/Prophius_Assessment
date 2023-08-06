package com.olamireDev.prophiusapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olamireDev.prophiusapi.ProphiusApiApplication;
import com.olamireDev.prophiusapi.payload.request.EditCommentDTO;
import com.olamireDev.prophiusapi.payload.request.LoginDTO;
import com.olamireDev.prophiusapi.payload.response.CommentDTO;
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
class CommentControllerTest {

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
    void getComment() throws Exception {
        var resultActions = mockMvc.perform(get("/api/v1/comments/{id}", 2L )
                        .header("Authorization", token))
                .andExpect(status().isOk());
        var CommentDTO = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), CreatedUserDTO.class);
        assertNotNull(CommentDTO);
    }

    @Test
    void getPostComments() throws Exception {
         mockMvc.perform(get("/api/v1/comments/post/{id}", 1 )
                         .header("Authorization", token))
                            .andExpect(status().isOk());
    }

    @Test
    void updateComment() throws Exception {
        var editCommentDTO = new EditCommentDTO(2L, "This is a test comment");
        var resultActions = mockMvc.perform(put("/api/v1/comments/update" )
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editCommentDTO)))
                .andExpect(status().isOk());
        var response = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(response.equals("It is not your place to edit this comment"));
    }

    @Test
    void deleteComment() throws Exception {
        var resultActions = mockMvc.perform(delete("/api/v1/comments/delete/{id}", 1 )
                        .header("Authorization", token))
                .andExpect(status().isOk());
        var response = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("comment deleted", response);
    }

    @Test
    void deleteCommentWrongUser() throws Exception {
        var resultActions = mockMvc.perform(delete("/api/v1/comments/delete/{id}", 3 )
                        .header("Authorization", token))
                .andExpect(status().isOk());
        var response = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("It is not your place to delete this comment", response);
    }

}