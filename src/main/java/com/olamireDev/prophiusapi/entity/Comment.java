package com.olamireDev.prophiusapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @OneToOne
    private Post post;

    @OneToOne
    private User commentedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
