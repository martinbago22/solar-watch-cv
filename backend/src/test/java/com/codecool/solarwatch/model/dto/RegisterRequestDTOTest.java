package com.codecool.solarwatch.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
// violations.iterator

class RegisterRequestDTOTest {
    Set<ConstraintViolation<RegisterRequestDTO>> violations = new HashSet<>();
    RegisterRequestDTO underTest = null;
    @AfterEach
    void tearDown() {
        violations.clear();
        underTest = null;
    }
    @Nested
    @Description("valami")
    class WhenDtoIsValid {
        @Test
        void későbbbefejezem() {
            underTest = new RegisterRequestDTO("valid", "doe");

            violations = validate(underTest);

            assertTrue(violations.isEmpty());
        }
    }
    @Nested
    @Description("valami")
    class WhenDtoIsInvalid {
        String actualViolationType = violations.iterator().next().getPropertyPath().toString();
    }
    private Set<ConstraintViolation<RegisterRequestDTO>> validate(RegisterRequestDTO underTest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(underTest);
    }
}