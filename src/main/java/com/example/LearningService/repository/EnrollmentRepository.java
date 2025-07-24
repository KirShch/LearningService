package com.example.LearningService.repository;

import com.example.LearningService.model.Enrollment;
import com.example.LearningService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    public List<Enrollment> findEnrollmentsByUser(User user);
}
