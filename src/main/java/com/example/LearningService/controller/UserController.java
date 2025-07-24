package com.example.LearningService.controller;

import com.example.LearningService.cervice.EnrollmentService;
import com.example.LearningService.cervice.UserService;
import com.example.LearningService.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody UserRegistrationDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Валидация не пройдена!
            List<String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();

            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ofNullable(userService.userRegistration(userDto));
    }

    @GetMapping("/users/{userId}/enrollments")
    public ResponseEntity<?> getUserEnrollments(@PathVariable UUID userId){
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByUser(userService.findById(userId)));
    }

}
