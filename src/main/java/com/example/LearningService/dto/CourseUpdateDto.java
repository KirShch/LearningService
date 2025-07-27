package com.example.LearningService.dto;

import com.example.LearningService.entity.CourseStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDto {
    private String title;

    private String description;

    //@Pattern(regexp = "DRAFT|PUBLISHED|ARCHIVED", message = "Should be one of: DRAFT, PUBLISHED, ARCHIVED")
    private CourseStatus status;
}
