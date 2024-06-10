package com.codecool.solarwatch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    @ExceptionHandler(InvalidUserNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidUserNameExceptionHandler(InvalidUserNameException e) {
        return e.getMessage();
    }
}
