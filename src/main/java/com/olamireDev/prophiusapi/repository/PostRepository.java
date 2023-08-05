package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Post;
import com.olamireDev.prophiusapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteAllByCreatedBy(User createdBy);
}
