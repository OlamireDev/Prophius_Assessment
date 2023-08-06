package com.olamireDev.prophiusapi.service.impl;

import com.olamireDev.prophiusapi.entity.Comment;
import com.olamireDev.prophiusapi.exception.CommentNotFoundException;
import com.olamireDev.prophiusapi.exception.PostNotFoundException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.EditCommentDTO;
import com.olamireDev.prophiusapi.payload.response.CommentDTO;
import com.olamireDev.prophiusapi.repository.CommentRepository;
import com.olamireDev.prophiusapi.repository.PostRepository;
import com.olamireDev.prophiusapi.repository.UserRepository;
import com.olamireDev.prophiusapi.service.CommentService;
import com.olamireDev.prophiusapi.util.ContextEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;


    @Override
    public CommentDTO getComment(Long commentId) throws CommentNotFoundException {
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("comment not found"));
        return CommentDTO.commentDTOBuilder(comment);
    }

    @Override
    public List<CommentDTO> getPostComments(Long postId) throws PostNotFoundException {
        var post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found"));
        return commentRepository.findAllByPost(post).stream().map(CommentDTO::commentDTOBuilder).toList();
    }

    @Override
    public String updateComment(EditCommentDTO editCommentDTO) throws CommentNotFoundException, UserNotFoundException {
        var user = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var comment = commentRepository.findById(editCommentDTO.CommentId()).orElseThrow(() -> new CommentNotFoundException("comment not found"));
        if(comment.getCommentedBy() == user){
            comment.setContent(editCommentDTO.content());
            commentRepository.save(comment);
            return "comment updated";
        }
        return "It is not your place to edit this comment";
    }

    @Override
    public String deleteComment(Long commentId) throws CommentNotFoundException, UserNotFoundException {
        var user = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("comment not found"));
        if(comment.getCommentedBy() == user){
            commentRepository.delete(comment);
            return "comment deleted";
        }
        return "It is not your place to delete this comment";
    }
}
