package com.codecool.solarwatch.api.weather.current_weather_response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MainWeatherInfo(@JsonProperty("temp") int temperature, @JsonProperty("humidity") double humidity) {
}
