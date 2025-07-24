package com.example.LearningService.dto;

import com.example.LearningService.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotBlank
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "password should not be empty")
    private String password; // захеширован

    @NotBlank(message = "firstName should not be empty")
    private String firstName;

    @NotBlank(message = "lastName should not be empty")
    private String lastName;

    @Pattern(regexp = "STUDENT|TEACHER|ADMIN", message = "Should be one of: STUDENT, TEACHER, ADMIN")
    private Role role;
}
