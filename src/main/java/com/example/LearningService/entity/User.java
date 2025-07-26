package com.example.LearningService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // захеширован

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role; // STUDENT, TEACHER, ADMIN

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

}
