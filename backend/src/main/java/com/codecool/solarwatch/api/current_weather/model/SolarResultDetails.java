package com.codecool.solarwatch.api.current_weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarResultDetails(@JsonProperty("sunrise") String sunrise,
                                 @JsonProperty("sunset") String sunset,
                                 LocalDate date) {
}
