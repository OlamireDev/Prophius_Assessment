package com.olamireDev.prophiusapi.service;

import com.olamireDev.prophiusapi.exception.AuthorizationException;
import com.olamireDev.prophiusapi.exception.BadRequestException;
import com.olamireDev.prophiusapi.exception.ExistingEmailException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.CreateUserDTO;
import com.olamireDev.prophiusapi.payload.request.EditUserDTO;
import com.olamireDev.prophiusapi.payload.request.LoginDTO;
import com.olamireDev.prophiusapi.payload.response.CreatedUserDTO;

public interface UserService {

    CreatedUserDTO createUser(CreateUserDTO createUserDTO) throws ExistingEmailException;
    CreatedUserDTO loginUser (LoginDTO loginDTO) throws UserNotFoundException;
    String editUser(EditUserDTO editUserDTO) throws UserNotFoundException;
    String followUnfollow(Long id, boolean follow) throws UserNotFoundException, BadRequestException;
    String deleteUser() throws UserNotFoundException;

}
