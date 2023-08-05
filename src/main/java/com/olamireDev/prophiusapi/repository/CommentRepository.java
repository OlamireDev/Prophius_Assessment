package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Comment;
import com.olamireDev.prophiusapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByCommentedBy(User commentedBy);
}
