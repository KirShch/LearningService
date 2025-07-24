package com.example.LearningService.controller;

import com.example.LearningService.cervice.CourseService;
import com.example.LearningService.dto.CreateCourseDto;
import com.example.LearningService.model.Course;
import com.example.LearningService.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;


    // добавить пагинацию
    @GetMapping("/courses")
    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    @GetMapping("/courses/{id}")
    public Course getCourses(@PathVariable UUID id){
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CreateCourseDto courseDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Валидация не пройдена!
            List<String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();

            return ResponseEntity.badRequest().body(errors);
        }

        Course course = courseService.createCourse(courseDto);

        return ResponseEntity.ok(courseRepository.save(course));
    }


}
