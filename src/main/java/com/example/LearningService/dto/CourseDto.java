package com.example.LearningService.dto;


import com.example.LearningService.entity.CourseStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    @NotNull(message = "title cannot be null")
    @Size(min = 1, max = 60, message = "title must between 1 and 60")
    private String title;

    private String description;

    private Long authorId;

    @NotNull(message = "Should be one of: DRAFT, PUBLISHED, ARCHIVED")
    private CourseStatus status;
}
