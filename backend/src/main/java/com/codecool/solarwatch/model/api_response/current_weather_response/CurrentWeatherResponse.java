package com.codecool.solarwatch.model.api_response.current_weather_response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentWeatherResponse(@JsonProperty("weather") CurrentWeatherDescription[] currentWeatherDescription,
                                     @JsonProperty("main") MainWeatherInfo mainWeatherInfo,
                                     @JsonProperty("wind") WindInfo windInfo,
                                     @JsonProperty("visibility") double visibility) {
}
