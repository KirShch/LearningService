package com.example.LearningService.repository;

import com.example.LearningService.entity.Enrollment;
import com.example.LearningService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    public List<Enrollment> findEnrollmentsByUser(User user);
}
