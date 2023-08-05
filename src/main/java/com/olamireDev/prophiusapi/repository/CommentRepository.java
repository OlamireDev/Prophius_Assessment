package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Comment;
import com.olamireDev.prophiusapi.entity.Post;
import com.olamireDev.prophiusapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByCommentedBy(User commentedBy);
    List<Comment> findAllByPost(Post post);
}
