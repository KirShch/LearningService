package com.example.LearningService.model;

import com.example.LearningService.model.RolesAndStatuses.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
