package com.example.LearningService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadInDto {
    private Long externalCourseId;
    private String title;
    private String description;
    private String authorEmail;
}
