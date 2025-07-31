package com.example.LearningService.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EnrollmentStatus status; // ACTIVE, COMPLETED, CANCELLED

    @Column(nullable = false)
    private Instant enrolledAt;

    @Column
    private Instant completedAt;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Enrollment that)) return false;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(enrolledAt, that.enrolledAt) && Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, enrolledAt, completedAt);
    }
}
