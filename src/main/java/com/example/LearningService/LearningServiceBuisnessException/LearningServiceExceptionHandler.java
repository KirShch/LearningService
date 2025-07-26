package com.example.LearningService.LearningServiceBuisnessException;

import com.example.LearningService.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class LearningServiceExceptionHandler {
    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(UserEmailExistsException ex, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatus(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(UserNotExistsException ex, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatus(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserIncorrectRoleException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(UserIncorrectRoleException ex, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatus(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
