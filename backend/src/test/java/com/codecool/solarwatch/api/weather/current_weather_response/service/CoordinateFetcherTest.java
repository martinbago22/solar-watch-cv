package com.codecool.solarwatch.api.weather.current_weather_response.service;

import com.codecool.solarwatch.model.Coordinates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

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
        Coordinates expected = new Coordinates(12, 12, "asd", "asd");
        Mono<Coordinates[]> mono = Mono.just(new Coordinates[]{expected});


        when(webClient.get().uri(any(String.class)).retrieve().bodyToMono(Coordinates[].class).block())
                .thenReturn(mono);
        Coordinates[] actual = coordinateFetcher.getCoordinatesForCity(cityName);

        assertEquals(expected, actual[0]);
    }
}