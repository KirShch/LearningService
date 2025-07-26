package com.example.LearningService.controller;

import com.example.LearningService.service.EnrollmentService;
import com.example.LearningService.service.UserService;
import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "message", "Validation failed",
                            "errors", errors
                    )
            );
        }

        return ResponseEntity.ofNullable(userService.userRegistration(userDto));
    }

    @GetMapping("/users/{userId}/enrollments")
    public ResponseEntity<?> getUserEnrollments(@PathVariable Long userId){
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByUser(userService.findById(userId)));
    }

    @GetMapping("/users/getall")
    public List<User> getAllUser(){
        System.out.println("--------------------------->>>>>>>>>>>>>");
        return userService.getAllUsers();
    }

}
