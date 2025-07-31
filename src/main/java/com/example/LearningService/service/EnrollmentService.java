package com.example.LearningService.service;

import com.example.LearningService.dto.EnrollmentDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.Enrollment;
import com.example.LearningService.entity.EnrollmentStatus;
import com.example.LearningService.entity.User;
import com.example.LearningService.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseService courseService;

    @Cacheable(value = "enrollmentsByUser", unless = "#result == null || #result.isEmpty()")
    public List<Enrollment> getEnrollmentsByUser(Long userId){
        User user = userService.findById(userId);
        return enrollmentRepository.findEnrollmentsByUser(user);
    }

    public Enrollment createEnrollment(EnrollmentDto enrollmentDto){
        User user = userService.findById(enrollmentDto.userId());
        Course course = courseService.findById(enrollmentDto.courseId());
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setEnrolledAt(Instant.now());
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> findAll(){
        return enrollmentRepository.findAll();
    }

    @Cacheable(value = "top5CoursesByEnrollments", unless = "#result == null || #result.isEmpty()")
    public List<Course> getTop5CoursesByEnrollments(){
        return findAll().stream()
                .collect(Collectors.groupingBy(Enrollment::getCourse
                        , Collectors.counting()))
                .entrySet().stream()
                .sorted((e1,e2)->-Long.compare(e1.getValue(),e2.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
