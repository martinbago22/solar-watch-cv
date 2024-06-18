package com.codecool.solarwatch.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
@Component
public class WeatherForecastControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidCityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notSupportedCityExceptionHandler(InvalidCityException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UnexpectedRollbackException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String unexpectedRollbackExceptionHandler(UnexpectedRollbackException e) {
        return "Unexpected rollback happened";
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String dataIntegrityViolationHandler(DataIntegrityViolationException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDateExceptionHandler(InvalidDateException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> onInvalidMethodArgumentExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> response = new HashMap<>();

        exception.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();

                    response.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
