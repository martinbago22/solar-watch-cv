package com.codecool.solarwatch.exception;

public class InvalidCityException extends RuntimeException {

    public InvalidCityException(String cityName) {
        super(String.format("No city found under name: %s", cityName));


    }
}
