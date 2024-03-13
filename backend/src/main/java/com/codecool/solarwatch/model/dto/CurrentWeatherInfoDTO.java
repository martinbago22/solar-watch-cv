package com.codecool.solarwatch.model.dto;

public record CurrentWeatherInfoDTO(String cityName, double temperature, double humidity, double windSpeed, String description) {
}
