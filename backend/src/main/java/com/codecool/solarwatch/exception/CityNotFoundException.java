package com.codecool.solarwatch.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String cityName) {
        super(String.format("No city found under name: %s", cityName));
    }
}
