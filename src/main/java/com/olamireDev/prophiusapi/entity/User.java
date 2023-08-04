package com.olamireDev.prophiusapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String profilePicture;
    @OneToMany
    private List<User> following;
    @OneToMany
    private List<User> followers;

}
