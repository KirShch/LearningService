package com.example.LearningService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInDto {
    private Long eventId;
    private String systemId;
    private PayloadInDto payload;
}
