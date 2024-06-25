package com.codecool.solarwatch.api.weather.current_weather_response.service;

import com.codecool.solarwatch.model.WeatherReport;
import com.codecool.solarwatch.model.entity.City;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CurrentWeatherFetcher Unit test")
class CurrentWeatherFetcherTest {
    private static MockWebServer mockBackEnd;

    private static final City city =
            new City("valid", 12, 12, "state", "country");
    private static final LocalDate date =
            LocalDate.now();
    @InjectMocks
    private CurrentWeatherFetcher currentWeatherFetcher;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }
    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        currentWeatherFetcher = new CurrentWeatherFetcher(baseUrl);
    }

    @Nested
    @DisplayName("Test cases for successful SunriseSunsetInfo fetching")
    class WhenFetchSunriseSunsetInfoIsSuccessful {
        @Test
        @DisplayName("asd")
        void WhenGivenValidCityAndDate_ThenSunriseSunsetInfoGetsFetched() {
            WeatherReport expected = mock(WeatherReport.class);
        }
    }
}