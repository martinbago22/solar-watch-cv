package com.codecool.solarwatch.exception;

import com.codecool.solarwatch.model.entity.City;

import java.time.LocalDate;

public class SunriseSunsetNotFoundException extends RuntimeException {
    public SunriseSunsetNotFoundException(City city, LocalDate localDate) {
        super(String.format("No sunrise/sunset info found in database for %s date and %s location", localDate, city.getName()));
    }
}
