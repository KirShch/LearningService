package com.example.LearningService.controller;

import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.service.CourseService;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.service.ValidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CourseController {
    private final CourseService courseService;
    private final ValidService validService;

    @GetMapping("/courses")
    public Page<Course> getCourses(Pageable pageable){
        return courseService.findAll(pageable);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourses(@PathVariable Long id){
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateDto courseUpdateDto){
        return ResponseEntity.ok(courseService.updateCourse(courseUpdateDto, id));
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseDto courseDto){
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id){
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }
}
