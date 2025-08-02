package com.example.LearningService.exception;

import org.springframework.http.HttpStatus;

public class UserIncorrectRoleException extends LearningServiceException{
    public UserIncorrectRoleException(String message){
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
