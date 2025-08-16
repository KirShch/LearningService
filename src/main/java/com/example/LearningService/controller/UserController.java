package com.example.LearningService.controller;

import com.example.LearningService.dto.EnrollmentDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.Enrollment;
import com.example.LearningService.service.EnrollmentService;
import com.example.LearningService.service.UserService;
import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.User;
import com.example.LearningService.service.ValidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "User API", description = "User and enrollment registration, getting information")
public class UserController {
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @Operation(summary = "User registration", description = "register User by received User DTO")
    @ApiResponse(responseCode = "200", description = "User added")
    @PostMapping("/auth/register")
    public User userRegistration(
            @Parameter(description = "User data", required = true)
            @Valid @RequestBody UserDto userDto
    ){
        log.info("UserDto for registration obtained (from REST post endpoint api/auth/register)");
        return userService.userRegistration(userDto);
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<Enrollment> getUserEnrollments(@PathVariable Long userId){
        return enrollmentService.getEnrollmentsByUser(userId);
    }

    @Operation(summary = "Getting all users", description = "get all users")
    @ApiResponse(responseCode = "200", description = "All users got")
    @GetMapping("/users/getall")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/courses/top5")
    public List<Course> getTop5CoursesByEnrollments(){
        return enrollmentService.getTop5CoursesByEnrollments();
    }

    @PostMapping("/users/enrollments")
    public Enrollment createEnrollment(@RequestBody EnrollmentDto enrollmentDto){
        log.info("EnrollmentDto obtained (from REST post endpoint api/users/enrollments)");
        return enrollmentService.createEnrollment(enrollmentDto);
    }
}
