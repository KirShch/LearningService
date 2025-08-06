package com.example.LearningService.repository;

import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.Enrollment;
import com.example.LearningService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findEnrollmentsByUser(User user);

    @Query("SELECT e.course FROM Enrollment e GROUP BY e.course ORDER BY COUNT(e.id) DESC LIMIT 5")
    List<Course> getTop5CoursesByEnrollments();
}
