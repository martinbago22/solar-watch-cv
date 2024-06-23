package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.weather.current_weather_response.service.CoordinateFetcher;
import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.Coordinates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("GeocodeService Unit test")
class GeoCodeServiceTest {
    @Mock
    CoordinateFetcher coordinateFetcher;
    @InjectMocks
    GeoCodeService geoCodeService;
    @ExtendWith(MockitoExtension.class)
    @Test
    void WhenGetCoordinatesFromCity_ThenCoordinatesGetReturned() {
        Coordinates expected = new Coordinates(0, 1, "Hungary", "Bács-Kiskun");

        when(coordinateFetcher.getCoordinatesForCity(anyString()))
                .thenReturn(expected);
        Coordinates actual = geoCodeService.getCoordinatesFromCity("asd");

        assertEquals(expected, actual);
    }

    @Test
    void WhenCityIsNull_ThenInvalidCityExceptionIsThrown() {
        when(coordinateFetcher.getCoordinatesForCity(anyString()))
                .thenReturn(null);

        assertThrows(InvalidCityException.class, () -> this.geoCodeService
                .getCoordinatesFromCity(null));
    }

    @Test
    void getCoordinatesFromCityThrowsInvalidCityExceptionForInvalidCityName() {
        assertThrows(InvalidCityException.class, () -> this.geoCodeService
                .getCoordinatesFromCity("123213"));
    }
}