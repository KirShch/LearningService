package com.example.LearningService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id")
    private User student;

    @Column
    private String submissionText;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AssignmentStatus status; // PENDING, SUBMITTED, GRADED

    @Column(nullable = false)
    private Integer grade;

    @Column
    private String teacherFeedback;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Assignment that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(submissionText, that.submissionText) && status == that.status && Objects.equals(grade, that.grade) && Objects.equals(teacherFeedback, that.teacherFeedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, submissionText, status, grade, teacherFeedback);
    }
}
