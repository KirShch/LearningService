package com.example.LearningService.dto;


import com.example.LearningService.model.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseDto {

    @NotBlank(message = "title cannot be null")
    @Size(min = 1, max = 60, message = "title must between 1 and 60")
    private String title;

    private String description;

    private UUID user_id;

    @Pattern(regexp = "DRAFT|PUBLISHED|ARCHIVED", message = "Should be one of: DRAFT, PUBLISHED, ARCHIVED")
    private CourseStatus status;
}
