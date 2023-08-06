package com.olamireDev.prophiusapi.service;

import com.olamireDev.prophiusapi.exception.InvalidOperationException;
import com.olamireDev.prophiusapi.exception.PostNotFoundException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.CreatePostDTO;
import com.olamireDev.prophiusapi.payload.request.EditPostDTO;
import com.olamireDev.prophiusapi.payload.request.PostPageRequestDTO;
import com.olamireDev.prophiusapi.payload.response.PostDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostDTO createPost(CreatePostDTO createPostDTO) throws UserNotFoundException;
    PostDTO getPost(Long id) throws PostNotFoundException;
    List<PostDTO> getAllPosts(Long userId) throws UserNotFoundException;
    String likePost(Long id) throws PostNotFoundException, UserNotFoundException, InvalidOperationException;
    String updatePost(EditPostDTO editPostDTO) throws UserNotFoundException, PostNotFoundException, InvalidOperationException;
    String deletePost(Long id) throws UserNotFoundException, PostNotFoundException, InvalidOperationException;
    Page<PostDTO> getAllPostPaged(PostPageRequestDTO pageRequest) throws UserNotFoundException;
}
