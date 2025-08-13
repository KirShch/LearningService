package com.example.LearningService.dto;

import com.example.LearningService.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO")
public class UserDto {

    @NotNull
    @Email(message = "invalid email")
    @Schema(description = "User email", example = "vasyap@mylo.py")
    private String email;

    @NotNull(message = "password should not be empty")
    @Size(min = 4)
    @Schema(description = "User password (minimum 4 symbols)", example = "qq78JMlkq!rQ0Mx")
    private String password;

    @NotNull(message = "firstName should not be empty")
    @Schema(description = "User first name", example = "Vasya")
    private String firstName;

    @NotNull(message = "lastName should not be empty")
    @Schema(description = "User last name", example = "Pupkin")
    private String lastName;

    @NotNull(message = "Should be one of: STUDENT, TEACHER, ADMIN")
    @Schema(description = "User role (should be one of: STUDENT, TEACHER, ADMIN)", example = "TEACHER")
    private Role role;
}
