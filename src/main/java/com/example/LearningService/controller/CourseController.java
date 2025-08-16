package com.example.LearningService.controller;

import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.service.CourseService;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.service.ValidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public Page<Course> getCourses(Pageable pageable){
        return courseService.findAll(pageable);
    }

    @GetMapping("/courses/{id}")
    public Course getCourses(@PathVariable Long id){
        return courseService.findById(id);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateDto courseUpdateDto){
        log.info("CourseUpdateDto obtained (from REST put endpoint api/auth/" + id + ")");
        return courseService.updateCourse(courseUpdateDto, id);
    }

    @PostMapping("/courses")
    public Course createCourse(@Valid @RequestBody CourseDto courseDto){
        log.info("CourseDto for course creation is obtained (from REST post endpoint api/courses)");
        return courseService.createCourse(courseDto);
    }

    @DeleteMapping("/courses/{id}")
    public Course deleteCourse(@PathVariable Long id){
        return courseService.deleteCourse(id);
    }
}
