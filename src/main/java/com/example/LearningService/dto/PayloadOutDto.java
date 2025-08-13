package com.example.LearningService.dto;

import com.example.LearningService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadOutDto {
    private Long externalCourseId;
    private Long lsCourseId;
    private CourseKafkaStatus status;
    private String authorEmail;
    private List<String> errors;
}
