package com.example.LearningService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    //@Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "author_id")
    private User author; // TEACHER or ADMIN

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CourseStatus status; // DRAFT, PUBLISHED, ARCHIVED

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Module> modules;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
