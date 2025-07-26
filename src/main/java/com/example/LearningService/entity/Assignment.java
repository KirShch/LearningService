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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
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
    public final boolean equals(Object o) {
        if (!(o instanceof Assignment that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
