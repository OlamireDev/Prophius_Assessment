package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.Post;
import com.olamireDev.prophiusapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedBy(User createdBy);
    void deleteAllByCreatedBy(User createdBy);

    Page<Post> findAllByCreatedBy(User users, Pageable pageable);

    Page<Post> findAllByCreatedByOrderByCreatedAtDesc(User users, Pageable pageable);

    Page<Post> findAllByCreatedByOrderByCreatedAtAsc(User users, Pageable pageable);

    Page<Post> findAllByCreatedByOrderByLikeCountAsc(User users, Pageable pageable);

    Page<Post> findAllByCreatedByOrderByLikeCountDesc(User users, Pageable pageable);
}
