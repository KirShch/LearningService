package com.example.LearningService.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int orderInCourse;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    private List<Lesson> lessons;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Module that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

}
