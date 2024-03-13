package com.codecool.solarwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Coordinates(@JsonProperty("lat") double latitude,
                          @JsonProperty("lon") double longitude,
                          @JsonProperty("country") String country,
                          @JsonProperty("state") String state) {
}
