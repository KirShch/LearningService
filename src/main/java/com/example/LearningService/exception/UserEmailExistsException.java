package com.example.LearningService.exception;

import org.springframework.http.HttpStatus;

public class UserEmailExistsException extends LearningServiceException{
    public UserEmailExistsException(String message){
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
