package com.example.LearningService.service;

import com.example.LearningService.entity.Enrollment;
import com.example.LearningService.entity.User;
import com.example.LearningService.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public List<Enrollment> getEnrollmentsByUser(User user){
        return enrollmentRepository.findEnrollmentsByUser(user);
    }
}
