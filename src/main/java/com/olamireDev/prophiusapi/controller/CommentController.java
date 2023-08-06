package com.olamireDev.prophiusapi.controller;

import com.olamireDev.prophiusapi.exception.CommentNotFoundException;
import com.olamireDev.prophiusapi.exception.PostNotFoundException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.EditCommentDTO;
import com.olamireDev.prophiusapi.payload.response.CommentDTO;
import com.olamireDev.prophiusapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody EditCommentDTO editCommentDTO) throws Exception {
        return ResponseEntity.ok(commentService.createComment(editCommentDTO));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable long commentId) throws Exception {
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getPostComments(@PathVariable long postId) throws PostNotFoundException {
        return ResponseEntity.ok(commentService.getPostComments(postId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody EditCommentDTO editCommentDTO) throws Exception{
        return ResponseEntity.ok(commentService.updateComment(editCommentDTO));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId) throws Exception{
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }

}
