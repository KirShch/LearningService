package com.example.LearningService.dto;

import com.example.LearningService.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
