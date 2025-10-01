package com.example.LearningService.controller;

import com.example.LearningService.dto.CourseUpdateDto;
import com.example.LearningService.service.CourseService;
import com.example.LearningService.dto.CourseDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.service.ValidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get courses", description = "Get courses in page style")
    @ApiResponse(responseCode = "200", description = "Courses got")
    @GetMapping("/courses")
    public Page<Course> getCourses(Pageable pageable){
        return courseService.findAll(pageable);
    }

    @Operation(summary = "Get course", description = "Get course by id")
    @ApiResponse(responseCode = "200", description = "Course got")
    @GetMapping("/courses/{id}")
    public Course getCourses(
            @Parameter(description = "Course id in path", required = true)
            @PathVariable Long id){
        return courseService.findById(id);
    }

    @Operation(summary = "Update course", description = "Update course by obtained DTO")
    @ApiResponse(responseCode = "200", description = "Course updated")
    @PutMapping("/courses/{id}")
    public Course updateCourse(
            @Parameter(description = "Course id in path", required = true)
            @PathVariable Long id,
            @Parameter(description = "Course update DTO", required = true)
            @Valid @RequestBody CourseUpdateDto courseUpdateDto){
        log.info("CourseUpdateDto obtained (from REST put endpoint api/auth/" + id + ")");
        return courseService.updateCourse(courseUpdateDto, id);
    }

    @Operation(summary = "Create course", description = "Create course by obtained DTO")
    @ApiResponse(responseCode = "200", description = "Course created")
    @PostMapping("/courses")
    public Course createCourse(
            @Parameter(description = "Course create DTO", required = true)
            @Valid @RequestBody CourseDto courseDto){
        log.info("CourseDto for course creation is obtained (from REST post endpoint api/courses)");
        return courseService.createCourse(courseDto);
    }

    @Operation(summary = "Delete course", description = "Delete course by id")
    @ApiResponse(responseCode = "200", description = "Course deleted")
    @DeleteMapping("/courses/{id}")
    public Course deleteCourse(
            @Parameter(description = "Course id in path", required = true)
            @PathVariable Long id){
        return courseService.deleteCourse(id);
    }
}
