package com.example.LearningService.controller;

import com.example.LearningService.dto.EnrollmentDto;
import com.example.LearningService.entity.Course;
import com.example.LearningService.entity.Enrollment;
import com.example.LearningService.service.EnrollmentService;
import com.example.LearningService.service.UserService;
import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.User;
import com.example.LearningService.service.ValidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @PostMapping("/auth/register")
    public User userRegistration(@Valid @RequestBody UserDto userDto){
        return userService.userRegistration(userDto);
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<Enrollment> getUserEnrollments(@PathVariable Long userId){
        return enrollmentService.getEnrollmentsByUser(userId);
    }

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
        return enrollmentService.createEnrollment(enrollmentDto);
    }
}
