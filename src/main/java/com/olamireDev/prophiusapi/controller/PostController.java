package com.olamireDev.prophiusapi.controller;

import com.olamireDev.prophiusapi.exception.InvalidOperationException;
import com.olamireDev.prophiusapi.exception.PostNotFoundException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.CreatePostDTO;
import com.olamireDev.prophiusapi.payload.request.EditPostDTO;
import com.olamireDev.prophiusapi.payload.response.PostDTO;
import com.olamireDev.prophiusapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostDTO createPostDTO) throws UserNotFoundException {
        return ResponseEntity.ok(postService.createPost(createPostDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) throws PostNotFoundException {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(postService.getAllPosts(userId));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<String> likePost(@PathVariable Long id) throws UserNotFoundException, PostNotFoundException, InvalidOperationException {
        return ResponseEntity.ok(postService.likePost(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePost(@RequestBody EditPostDTO editPostDTO) throws UserNotFoundException, PostNotFoundException, InvalidOperationException {
        return ResponseEntity.ok(postService.updatePost(editPostDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) throws UserNotFoundException, PostNotFoundException, InvalidOperationException {
        return ResponseEntity.ok(postService.deletePost(id));
    }

}
