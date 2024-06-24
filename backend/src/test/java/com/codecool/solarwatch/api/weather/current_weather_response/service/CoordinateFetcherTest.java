package com.codecool.solarwatch.api.weather.current_weather_response.service;

import com.codecool.solarwatch.model.Coordinates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@DisplayName("CoordinateFetcher Unit test")
@ExtendWith(MockitoExtension.class)
class CoordinateFetcherTest {
    @Mock
    private WebClient webClient;
    @InjectMocks
    CoordinateFetcher coordinateFetcher;

    @Test
    void WhenCityNameIsValid_ThenProperCoordinatesGetFetched() {
        String cityName = "Budapest";
        Coordinates mockCoordinates = new Coordinates(12, 12, "asd", "asd");
        List<Coordinates> coordinatesList = List.of(mockCoordinates);

    }
}