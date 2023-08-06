package com.olamireDev.prophiusapi.service.impl;

import com.olamireDev.prophiusapi.entity.User;
import com.olamireDev.prophiusapi.exception.AuthorizationException;
import com.olamireDev.prophiusapi.exception.BadRequestException;
import com.olamireDev.prophiusapi.exception.ExistingEmailException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.CreateUserDTO;
import com.olamireDev.prophiusapi.payload.request.EditUserDTO;
import com.olamireDev.prophiusapi.payload.request.LoginDTO;
import com.olamireDev.prophiusapi.payload.response.CreatedUserDTO;
import com.olamireDev.prophiusapi.repository.CommentRepository;
import com.olamireDev.prophiusapi.repository.PostRepository;
import com.olamireDev.prophiusapi.repository.UserRepository;
import com.olamireDev.prophiusapi.security.JwtService;
import com.olamireDev.prophiusapi.service.UserService;
import com.olamireDev.prophiusapi.util.ContextEmail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @Override
    public CreatedUserDTO createUser(CreateUserDTO createUserDTO) throws ExistingEmailException {
        var dbUser = userRepository.findByEmail(createUserDTO.email());
        if(dbUser.isPresent()){
            throw new ExistingEmailException(String.format("User with the email %s, already exists", createUserDTO.email()));
        }
        System.out.println(createUserDTO);
        var newUser = User.builder().username(createUserDTO.username()).email(createUserDTO.email())
                .password(passwordEncoder.encode(createUserDTO.password())).profilePicture(createUserDTO.profilePicture())
                .build();
        newUser = userRepository.save(newUser);
        return CreatedUserDTO.builder().id(newUser.getId()).userName(newUser.getUsername()).build();
    }

    @Override
    public CreatedUserDTO loginUser(LoginDTO loginDTO) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        String token = "Bearer " + JwtService.generateToken
                (new org.springframework.security.core.userdetails.User(loginDTO.email(), loginDTO.password(),
                        new ArrayList<>()));
        User user = userRepository.findByEmail(loginDTO.email()).orElseThrow(()-> new UserNotFoundException("User doesnt exist"));
        return CreatedUserDTO.builder().id(user.getId()).userName(user.getUsername()).token(token).build();
    }

    @Override
    public String editUser(EditUserDTO editUserDTO) throws UserNotFoundException {
        List<String> changed = new ArrayList<>();
        var user = userRepository.findByEmail(getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        if(editUserDTO.getUserName() !=null){
            user.setUsername(editUserDTO.getUserName());
            changed.add("Username");
        }
        if(editUserDTO.getPassword() != null){
            user.setPassword(passwordEncoder.encode(editUserDTO.getPassword()));
            changed.add("Password");
        }
        if(editUserDTO.getProfilePicture() != null){
            user.setProfilePicture(editUserDTO.getProfilePicture());
            changed.add("Profile picture");
        }
        return String.format("Successfully changed %s", String.join(", ", changed));
    }

    @Override
    public String followUnfollow(Long id, boolean follow) throws UserNotFoundException, BadRequestException {
        var requestingUser = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var targetUser = userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Target user Not found"));
        if(requestingUser.getId().equals(targetUser.getId())){
            throw new BadRequestException("You cannot follow/unfollow yourself");
        }
        if(follow && !targetUser.getFollowers().contains(requestingUser)){
            targetUser.getFollowers().add(requestingUser);
            requestingUser.getFollowing().add(targetUser);
            userRepository.save(targetUser);
            userRepository.save(requestingUser);
            return "Successfully followed user";
        }else if(!follow && targetUser.getFollowers().contains(requestingUser)){
            targetUser.getFollowers().remove(requestingUser);
            requestingUser.getFollowing().remove(targetUser);
            userRepository.save(targetUser);
            userRepository.save(requestingUser);
            return "Successfully unfollowed user";
        }else{
            throw new BadRequestException("Bad follow/unfollow request");
        }
    }

    @Override
    public String deleteUser() throws UserNotFoundException {
        var requestingUser = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        commentRepository.deleteAll(commentRepository.findAllByCommentedBy(requestingUser));
        postRepository.deleteAll(postRepository.findAllByCreatedBy(requestingUser));
        userRepository.delete(requestingUser);
        return "Successfully deleted "+ requestingUser.getUsername();
    }

    public String getEmail(){
        return ContextEmail.getEmail();
    }
}
