package com.example.LearningService.LearningServiceBuisnessException;

import org.springframework.http.HttpStatus;

public class LearningServiceException extends RuntimeException{
    private final HttpStatus status;

    public LearningServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
