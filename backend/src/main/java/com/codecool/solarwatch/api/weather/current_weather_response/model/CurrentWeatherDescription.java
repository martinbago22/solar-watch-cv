package com.codecool.solarwatch.api.weather.current_weather_response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentWeatherDescription(@JsonProperty("description") String description) {
}
