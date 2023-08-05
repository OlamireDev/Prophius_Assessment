package com.olamireDev.prophiusapi.service;

public interface PostService {
    String createPost(String content);
    String getPost(Long id);
    String likePost(Long id);
    String updatePost(Long id, String content);
    String deletePost(Long id);
}
