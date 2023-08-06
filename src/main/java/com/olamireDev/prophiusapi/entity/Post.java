package com.olamireDev.prophiusapi.entity;

import com.olamireDev.prophiusapi.payload.response.CommentDTO;
import com.olamireDev.prophiusapi.payload.response.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name ="posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private int likeCount;
    @OneToOne
    private User createdBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany
    private List<Comment> comments = new LinkedList<>();

}
