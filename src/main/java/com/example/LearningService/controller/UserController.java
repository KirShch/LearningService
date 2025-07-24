package com.example.LearningService.controller;

import com.example.LearningService.cervice.UserService;
import com.example.LearningService.dto.CreateCourseDto;
import com.example.LearningService.dto.UserRegistrationDto;
import com.example.LearningService.model.Course;
import com.example.LearningService.model.User;
import com.example.LearningService.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

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

    @GetMapping("/allusers")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
