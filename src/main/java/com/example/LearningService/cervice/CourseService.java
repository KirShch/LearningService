package com.example.LearningService.cervice;

import com.example.LearningService.dto.CreateCourseDto;
import com.example.LearningService.model.Course;
import com.example.LearningService.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public Course createCourse(CreateCourseDto courseDto){
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setStatus(courseDto.getStatus());
        course.setCreatedAt(LocalDateTime.now());
        return course;
    }
}
