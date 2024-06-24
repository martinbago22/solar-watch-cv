package com.codecool.solarwatch.api.weather.current_weather_response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentWeatherResponse(@JsonProperty("weather") CurrentWeatherDescription[] currentWeatherDescription,
                                     @JsonProperty("main") MainWeatherInfo mainWeatherInfo,
                                     @JsonProperty("wind") WindInfo windInfo,
                                     @JsonProperty("visibility") double visibility,
                                     @JsonProperty("sys") CurrentWeatherAPISunriseSunsetTime currentWeatherAPISunriseSunsetTime) {
}
