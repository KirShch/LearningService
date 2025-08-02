package com.example.LearningService.exception;

import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends LearningServiceException{
    public CourseNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
