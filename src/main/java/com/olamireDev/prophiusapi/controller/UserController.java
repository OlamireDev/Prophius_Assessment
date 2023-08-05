package com.olamireDev.prophiusapi.controller;

import com.olamireDev.prophiusapi.exception.AuthorizationException;
import com.olamireDev.prophiusapi.exception.BadRequestException;
import com.olamireDev.prophiusapi.exception.ExistingEmailException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.CreateUserDTO;
import com.olamireDev.prophiusapi.payload.request.EditUserDTO;
import com.olamireDev.prophiusapi.payload.request.LoginDTO;
import com.olamireDev.prophiusapi.payload.response.CreatedUserDTO;
import com.olamireDev.prophiusapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreatedUserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) throws ExistingEmailException {
        return ResponseEntity.ok(userService.createUser(createUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<CreatedUserDTO> loginUser(@RequestBody LoginDTO loginDTO) throws UserNotFoundException, AuthorizationException {
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }

    @PutMapping("/editUser")
    public ResponseEntity<String> editUser( @RequestBody EditUserDTO editUserDTO) throws UserNotFoundException {
        return ResponseEntity.ok(userService.editUser(editUserDTO));
    }

    @PutMapping("/follow/{id}")
    public ResponseEntity<String> followUser(@PathVariable Long id) throws UserNotFoundException, BadRequestException {
        return ResponseEntity.ok(userService.followUnfollow(id, true));
    }

    @PutMapping("/unfollow/{id}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long id, Long myId) throws UserNotFoundException, BadRequestException {
        return ResponseEntity.ok(userService.followUnfollow(id, false));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() throws UserNotFoundException {
        return ResponseEntity.ok(userService.deleteUser());
    }
}
