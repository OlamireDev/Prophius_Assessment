package com.olamireDev.prophiusapi.entity;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    private String profilePicture;
    @OneToMany
    private List<Post> posts;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<User> following = new LinkedList<>();
    @OneToMany
    private List<User> followers = new LinkedList<>();

}
