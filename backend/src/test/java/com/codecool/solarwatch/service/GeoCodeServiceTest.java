package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoCodeServiceTest {

    CoordinateFetcher coordinateFetcher = mock(CoordinateFetcher.class);
    GeoCodeService geoCodeService = new GeoCodeService(coordinateFetcher);

    @Test
    void getCoordinatesFromCityReturnsProperCoordinatesForValidCityName() {
        when(coordinateFetcher.getCoordinatesForCity(anyString()))
                .thenReturn(new Coordinates[]{new Coordinates(0, 1, "Hungary", "Bács-Kiskun")});

        Coordinates expected = new Coordinates(0, 1, "Hungary", "Bács-Kiskun");
        Coordinates actual = geoCodeService.getCoordinatesFromCity("asd");

        assertEquals(expected, actual);
    }

    @Test
    void getCoordinatesFromCityThrowsInvalidCityExceptionWhenCityIsNull() {
        assertThrows(InvalidCityException.class, () -> this.geoCodeService
                .getCoordinatesFromCity(null));
    }

    @Test
    void getCoordinatesFromCityThrowsInvalidCityExceptionForInvalidCityName() {
        assertThrows(InvalidCityException.class, () -> this.geoCodeService
                .getCoordinatesFromCity("123213"));
    }
}