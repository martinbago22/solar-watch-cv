package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.geocoding.service.GeoCodeAPIFetcher;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("SolarWatchService Unit test")
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

    @Nested
    @DisplayName("getSunriseSunsetInfo method tests")
    class GetSunriseSunsetInfoTests {
        @Test
        @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided an invalid date")
        void WhenProvidedInvalidDateParameter_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
            String invalidDate = "asd";
            String cityName = "asd";
            String expectedErrorMessage = "Date is in invalid format. Please provide it with YYYY-MM-DD";

            InvalidDateException exception = assertThrows(InvalidDateException.class,
                    () -> solarWatchService.getSunriseSunsetInfo(cityName, invalidDate));


            verify(cityRepository, times(0)).findByName(cityName);
            assertEquals(expectedErrorMessage, exception.getMessage());
        }
    }
}