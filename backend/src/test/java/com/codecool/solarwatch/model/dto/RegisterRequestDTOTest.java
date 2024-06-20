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

import static org.junit.jupiter.api.Assertions.*;

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
        void WhenDtoHasValidUsername_ThenViolationsIsEmpty() {
            underTest = new RegisterRequestDTO("valid", "doe");

            violations = validate(underTest);

            assertTrue(violations.isEmpty());
        }
    }

    @Nested
    @DisplayName("Test cases for invalid DTO requests")
    class WhenDtoIsInvalid {
        @Test
        @DisplayName("Validation fails when username is too long")
        void WhenUsernameIsTooLong_ThenValidationFails() {
            underTest = new RegisterRequestDTO("longerThanTenCharacters", "doe");

            violations = validate(underTest);
            String expectedViolationMessage = "Username must be between 4 and 10 characters long";
            String actualViolationMessage = violations.iterator().next().getMessage();

            assertFalse(violations.isEmpty(), "Expected violations but got none");
            assertEquals(expectedViolationMessage, actualViolationMessage);
        }

        @Test
        @DisplayName("Validation fails when username is too short")
        void WhenUsernameIsTooShort_ThenValidationFails() {
            underTest = new RegisterRequestDTO("one", "doe");

            violations = validate(underTest);
            String expectedViolationMessage = "Username must be between 4 and 10 characters long";
            String actualViolationMessage = getViolationMessage(violations);

            assertFalse(violations.isEmpty());
            assertEquals(expectedViolationMessage, actualViolationMessage);
        }

        @Test
        @DisplayName("Validation fails when username contains special characters")
        void WhenUsernameContainsSpecialCharacters_ThenValidationFails() {
            underTest = new RegisterRequestDTO("@£&$", "doe");

            violations = validate(underTest);
            String expectedViolationMessage = "Username cannot contain any special characters";
            String actualViolationMessage = getViolationMessage(violations);

            assertFalse(violations.isEmpty());
            assertEquals(expectedViolationMessage, actualViolationMessage);
        }
    }

    private Set<ConstraintViolation<RegisterRequestDTO>> validate(RegisterRequestDTO underTest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(underTest);
    }
    private String getViolationMessage(Set<ConstraintViolation<RegisterRequestDTO>> violations) {
        return violations.iterator().next().getMessage();
    }
}