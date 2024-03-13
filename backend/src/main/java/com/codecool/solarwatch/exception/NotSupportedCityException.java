package com.codecool.solarwatch.exception;

public class NotSupportedCityException extends RuntimeException {
    public NotSupportedCityException(String cityName) {
        super(String.format("City %s is not supported!", cityName));
    }
}
