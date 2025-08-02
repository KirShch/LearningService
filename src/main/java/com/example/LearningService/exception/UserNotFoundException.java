package com.example.LearningService.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends LearningServiceException{
    public UserNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
