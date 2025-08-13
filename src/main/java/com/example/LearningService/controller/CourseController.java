package com.example.LearningService.controller;

import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.service.CourseService;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.service.ValidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
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
        return courseService.updateCourse(courseUpdateDto, id);
    }

    @PostMapping("/courses")
    public Course createCourse(@Valid @RequestBody CourseDto courseDto){
        return courseService.createCourse(courseDto);
    }

    @DeleteMapping("/courses/{id}")
    public Course deleteCourse(@PathVariable Long id){
        return courseService.deleteCourse(id);
    }
}
