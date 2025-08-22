package com.example.LearningService.service;

import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.entity.CourseStatus;
import com.example.LearningService.exception.CourseNotFoundException;
import com.example.LearningService.exception.UserIncorrectRoleException;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.Role;
import com.example.LearningService.entity.User;
import com.example.LearningService.exception.UserNotFoundException;
import com.example.LearningService.mapper.CourseMapper;
import com.example.LearningService.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserService userService;

    public Course createCourse(CourseDto courseDto){
        Course course = buildCourse(courseDto);
        return courseRepository.save(course);
    }

    public Course buildCourse(CourseDto courseDto){
        Course course = courseMapper.toEntity(courseDto);
        course.setAuthor(loadAuthor(courseDto.getAuthorId()));
        course.setCreatedAt(Instant.now());
        return course;
    }

    private User loadAuthor(Long id){
        User user = userService.findById(id);
        verifyAuthor(user);
        return user;
    }

    public void verifyAuthor(User user){
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.TEACHER)
            throw new UserIncorrectRoleException("User role is incorrect");
    }

    @Cacheable(value = "course") //unless = "#result == null || #result.isEmpty()") // у типа Course нет метода isEmpty()
    public Course findById(Long id){
        if (id == null)
            throw new CourseNotFoundException("Course not found, id is null");
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found, id: " + id));
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Page<Course> findAll(Pageable pageable){
        return courseRepository.findAll(pageable);
    }

    @CachePut(cacheNames = "course", key = "#id")
    public Course updateCourse(CourseUpdateDto courseUpdateDto, Long id){
        Course course = getUpdatedCourse(courseUpdateDto, id);
        return courseRepository.save(course);
    }

    private Course getUpdatedCourse(CourseUpdateDto courseUpdateDto, Long id){
        Course course = findById(id);
        if (courseUpdateDto.getTitle() != null) course.setTitle(courseUpdateDto.getTitle());
        if (courseUpdateDto.getDescription() != null) course.setDescription(courseUpdateDto.getDescription());
        if (courseUpdateDto.getStatus() != null) course.setStatus(courseUpdateDto.getStatus());
        course.setUpdatedAt(Instant.now());
        return course;
    }

    public Course deleteCourse(Long id){
        Course course = findById(id);
        course.setStatus(CourseStatus.ARCHIVED);
        course.setUpdatedAt(Instant.now());
        return courseRepository.save(course);
    }
}
