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
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private LessonType type; // TEXT, VIDEO, QUIZ, ASSIGNMENT

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Column
    private boolean isPreview;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lesson lesson)) return false;
        return isPreview == lesson.isPreview && Objects.equals(id, lesson.id) && Objects.equals(title, lesson.title) && Objects.equals(content, lesson.content) && type == lesson.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, type, isPreview);
    }
}
