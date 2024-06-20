package com.codecool.solarwatch.model.dto;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
//TODO DTO-t tesztelni.

public record RegisterRequestDTO(
        @Pattern(regexp = "^[a-zA-Z0-9.]*$", message = "Username cannot contain any special characters")
        @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters long")
        String username,
        String password) {
}
