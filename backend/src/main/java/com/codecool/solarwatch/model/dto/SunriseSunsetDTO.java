package com.codecool.solarwatch.model.dto;

import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public record SunriseSunsetDTO(String cityName, LocalDate date, LocalTime sunriseTime,
                               LocalTime sunsetTime) implements Serializable {

    public SunriseSunsetDTO(SunriseSunsetInfo sunriseSunsetInfo) {
        this(sunriseSunsetInfo.getCity().getName(),
                sunriseSunsetInfo.getDate(),
                sunriseSunsetInfo.getSunriseTime(),
                sunriseSunsetInfo.getSunsetTime());
    }
}
