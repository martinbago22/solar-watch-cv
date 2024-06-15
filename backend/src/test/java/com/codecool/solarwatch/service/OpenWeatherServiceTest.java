package com.codecool.solarwatch.service;

import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OpenWeatherServiceTest {
    private static final String API = "https://api.sunrise-sunset.org/json?";
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherServiceTest.class);
    @Mock
    private WebClient webClient;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;
    @Mock
    private GeoCodeService geoCodeService;
    @InjectMocks
    OpenWeatherService openWeatherService;
    @Test
    void asd(){}
}