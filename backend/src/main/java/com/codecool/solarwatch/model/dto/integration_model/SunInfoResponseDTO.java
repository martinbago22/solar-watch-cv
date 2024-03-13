package com.codecool.solarwatch.model.dto.integration_model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunInfoResponseDTO(@JsonProperty("cityName") String cityName,
                                 @JsonProperty("date") String date) {
}
