package com.example.LearningService.controller;

import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.service.CourseService;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.repository.CourseRepository;
import com.example.LearningService.service.ValidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getCourses(@PathVariable Long id){
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateDto courseUpdateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return validService.getValidResponse(bindingResult);
        return ResponseEntity.ok(courseService.updateCourse(courseUpdateDto, id));
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return validService.getValidResponse(bindingResult);
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }


}
