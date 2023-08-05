package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Post;
import com.olamireDev.prophiusapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedBy(User createdBy);
    void deleteAllByCreatedBy(User createdBy);
}
