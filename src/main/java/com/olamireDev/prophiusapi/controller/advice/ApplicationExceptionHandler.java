package com.olamireDev.prophiusapi.controller.advice;

import com.olamireDev.prophiusapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        if(e instanceof UserNotFoundException|| e instanceof PostNotFoundException || e instanceof CommentNotFoundException){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        if(e instanceof AuthorizationException || e instanceof AuthenticationException){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        if(e instanceof ExistingEmailException){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
