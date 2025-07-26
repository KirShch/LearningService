package com.example.LearningService.controller;

import com.example.LearningService.service.CourseService;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CourseController {
    private final CourseService courseService;


    // добавить пагинацию
    @GetMapping("/courses")
    public List<Course> getCourses(){
        return courseService.findAll();
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourses(@PathVariable Long id){
        return ResponseEntity.ofNullable(courseService.findById(id));
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto){
        return ResponseEntity.ofNullable(courseService.createCourse(courseDto));
    }


}
