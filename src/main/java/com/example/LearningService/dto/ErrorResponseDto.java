package com.example.LearningService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String error;
    private String message;
    private String path;

    // Конструкторы, геттеры и сеттеры
    public ErrorResponseDto(HttpStatus status, String message, String path) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
