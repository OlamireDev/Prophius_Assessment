package com.olamireDev.prophiusapi.service;

import com.olamireDev.prophiusapi.exception.CommentNotFoundException;
import com.olamireDev.prophiusapi.exception.PostNotFoundException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.EditCommentDTO;
import com.olamireDev.prophiusapi.payload.response.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO getComment(Long commentId) throws UserNotFoundException, CommentNotFoundException;

    List<CommentDTO> getPostComments(Long postId) throws PostNotFoundException;

    String updateComment(EditCommentDTO editCommentDTO) throws CommentNotFoundException;

    String deleteComment(Long commentId) throws CommentNotFoundException;

}
