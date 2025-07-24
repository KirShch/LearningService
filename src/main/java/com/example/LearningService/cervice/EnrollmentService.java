package com.example.LearningService.cervice;

import com.example.LearningService.model.Enrollment;
import com.example.LearningService.model.User;
import com.example.LearningService.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public List<Enrollment> getEnrollmentsByUser(User user){
        return enrollmentRepository.findEnrollmentsByUser(user);
    }
}
