package com.example.LearningService.LearningServiceBuisnessException;

import org.springframework.http.HttpStatus;

public class UserNotExistsException extends LearningServiceException{
    public UserNotExistsException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
