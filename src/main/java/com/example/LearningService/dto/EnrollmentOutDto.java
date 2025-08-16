package com.example.LearningService.dto;

import com.example.LearningService.entity.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentOutDto implements Serializable {
    private Long eventId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant occurredAt;
    private Long enrollmentId;
    private Long courseId;
    private Long userId;
    private EnrollmentStatus status;
    private String sourceService;

}
