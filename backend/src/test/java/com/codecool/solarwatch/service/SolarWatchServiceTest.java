package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.geocoding.service.GeoCodeAPIFetcher;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.NotSupportedCityException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@DisplayName("OpenWeatherService Unit test")
@ExtendWith(MockitoExtension.class)
class SolarWatchServiceTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;
    @Mock
    private GeoCodeAPIFetcher geoCodeAPIFetcher;
    @InjectMocks
    SolarWatchService solarWatchService;

    @Test
    @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided invalid date")
    void WhenProvidedInvalidDateParameter_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
        assertThrows(InvalidDateException.class,
                () -> solarWatchService.getSunriseSunsetInfo("asd", "asd"));
    }
}