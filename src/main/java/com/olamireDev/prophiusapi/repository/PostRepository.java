package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
