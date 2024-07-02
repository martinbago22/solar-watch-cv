package com.codecool.solarwatch.api.weather.current_weather_response.service;

import com.codecool.solarwatch.api.current_weather.service.CurrentWeatherAPIFetcher;
import com.codecool.solarwatch.model.entity.City;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@DisplayName("CurrentWeatherFetcher Unit test")
class CurrentWeatherFetcherTest {
    private static MockWebServer mockBackEnd;

    private static final City city =
            new City("valid", 12, 12, "state", "country");
    private static final LocalDate date =
            LocalDate.now();
    private static CurrentWeatherAPIFetcher underTest;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        WebClient mockedWebClient = WebClient.builder()
                .baseUrl(mockBackEnd.url("/").toString())
                .build();
        underTest = new CurrentWeatherAPIFetcher(mockedWebClient);
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.close();
    }

    @Nested
    @DisplayName("Test cases for successful SunriseSunsetInfo fetching")
    class WhenFetchSunriseSunsetInfoIsSuccessful {
        @Test
        @DisplayName("asd")
        void WhenGivenValidCityAndDate_ThenSunriseSunsetInfoGetsFetched() {
        }
    }
}