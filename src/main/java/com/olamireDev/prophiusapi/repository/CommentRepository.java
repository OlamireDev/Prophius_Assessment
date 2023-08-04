package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
