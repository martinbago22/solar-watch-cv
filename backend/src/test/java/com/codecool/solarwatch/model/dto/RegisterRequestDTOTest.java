package com.codecool.solarwatch.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("RegisterRequestDTO Unit test")
class RegisterRequestDTOTest {
    Set<ConstraintViolation<RegisterRequestDTO>> violations = new HashSet<>();
    RegisterRequestDTO underTest = null;
    @AfterEach
    void tearDown() {
        violations.clear();
        underTest = null;
    }
    @Nested
    @DisplayName("Test cases for valid DTO requests")
    class WhenDtoIsValid {
        @Test
        @DisplayName("No violations occur when receiving valid username from request")
        void Violations_IsEmpty_WhenDtoHasValidUsername() {
            underTest = new RegisterRequestDTO("valid", "doe");

            violations = validate(underTest);

            assertTrue(violations.isEmpty());
        }
    }
    @Nested
    @DisplayName("Test cases for invalid DTO requests")
    class WhenDtoIsInvalid {
        @Test
        void RegisterRequest_ViolatesMaxCharacterConstraint_WhenUsernameIsLongerThanEightCharacters

    }
    private Set<ConstraintViolation<RegisterRequestDTO>> validate(RegisterRequestDTO underTest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(underTest);
    }
}