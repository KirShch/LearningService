package com.example.LearningService.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author; // TEACHER or ADMIN

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CourseStatus status; // DRAFT, PUBLISHED, ARCHIVED

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Module> modules;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    /*@Override
    public boolean equals(Object o) {
        if (!(o instanceof Course course)) return false;
        return Objects.equals(id, course.id) && Objects.equals(title, course.title) && Objects.equals(description, course.description) && status == course.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status);
    }*/

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Course that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
