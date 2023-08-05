package com.olamireDev.prophiusapi.payload.request;

public record CreateUserDTO(String username, String email, String password, String profilePicture) {
}
