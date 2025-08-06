package com.example.LearningService.dto;

import com.example.LearningService.entity.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "password should not be empty")
    @Size(min = 4)
    private String password;

    @NotNull(message = "firstName should not be empty")
    private String firstName;

    @NotNull(message = "lastName should not be empty")
    private String lastName;

    @NotNull(message = "Should be one of: STUDENT, TEACHER, ADMIN")
    private Role role;
}
