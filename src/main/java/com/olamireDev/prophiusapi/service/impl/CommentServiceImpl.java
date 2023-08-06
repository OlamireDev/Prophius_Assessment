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
    public CommentDTO createComment(EditCommentDTO editCommentDTO) throws PostNotFoundException, UserNotFoundException {
        var user = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var post = postRepository.findById(editCommentDTO.postOrCommentId())
                .orElseThrow(() -> new PostNotFoundException("post not found"));
        var comment = Comment.builder().content(editCommentDTO.content()).commentedBy(user).post(post).build();
        comment = commentRepository.save(comment);
        return CommentDTO.commentDTOBuilder(comment);
    }

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
    public String updateComment(EditCommentDTO editCommentDTO) throws CommentNotFoundException {
        var comment = commentRepository.findById(editCommentDTO.postOrCommentId()).orElseThrow(() -> new CommentNotFoundException("comment not found"));
        if(editCommentDTO.content() != null && !editCommentDTO.content().isEmpty()){
            comment.setContent(editCommentDTO.content());
            commentRepository.save(comment);
            return "comment updated";
        }
        return "comment not updated, because content is empty";
    }

    @Override
    public String deleteComment(Long commentId) throws CommentNotFoundException {
        commentRepository.delete(commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("comment not found")));
        return "comment deleted";
    }
}
