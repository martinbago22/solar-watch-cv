package com.codecool.solarwatch.dto;

import com.codecool.solarwatch.model.entity.SunriseSunset;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public record SunriseSunsetDTO(String cityName, LocalDate date, LocalTime sunriseTime,
                               LocalTime sunsetTime) implements Serializable {

    public SunriseSunsetDTO(SunriseSunset sunriseSunset) {
        this(sunriseSunset.getCity().getName(),
                sunriseSunset.getDate(),
                sunriseSunset.getSunriseTime(),
                sunriseSunset.getSunsetTime());
    }
}
