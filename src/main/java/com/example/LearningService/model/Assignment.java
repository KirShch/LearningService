package com.example.LearningService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    //@Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "lesson_id")
    private Lesson lesson;

    //@Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "user_id")
    private User student;

    private String submissionText;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AssignmentStatus status; // PENDING, SUBMITTED, GRADED

    @Column(nullable = false)
    private Integer grade;

    private String teacherFeedback;

}
