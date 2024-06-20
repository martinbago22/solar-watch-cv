package com.codecool.solarwatch.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Username cannot be empty")
        @Pattern(regexp = "^[a-zA-Z0-9.]*$", message = "Username cannot contain any special characters")
        @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters long")
        String username,
        @NotBlank(message = "Password must be minimum 4 and maximum 10 characters long")
        @Size(message = "Password must be minimum 4 and maximum 10 characters long")
        String password) {
}
