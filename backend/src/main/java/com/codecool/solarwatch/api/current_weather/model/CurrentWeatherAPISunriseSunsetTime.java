package com.codecool.solarwatch.api.current_weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentWeatherAPISunriseSunsetTime(@JsonProperty("sunrise") String sunriseUnixUTC,
                                                 @JsonProperty("sunset") String sunsetUnixUTC) {
}
