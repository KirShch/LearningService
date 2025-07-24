package com.example.LearningService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    //@Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    //@Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EnrollmentStatus status; // ACTIVE, COMPLETED, CANCELLED

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    private LocalDateTime completedAt;
}
