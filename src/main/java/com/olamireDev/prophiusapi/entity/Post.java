package com.olamireDev.prophiusapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private int likeCount;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany
    private List<Comment> comments;
}
