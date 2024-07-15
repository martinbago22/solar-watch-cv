package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.geocoding.service.GeoCodeAPIFetcher;
import com.codecool.solarwatch.exception.CityNotFoundException;
import com.codecool.solarwatch.api.geocoding.model.Coordinates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("GeocodeService Unit test")
@ExtendWith(MockitoExtension.class)
class GeoCodeAPIFetcherTest {

    @InjectMocks
    GeoCodeAPIFetcher geoCodeAPIFetcher;

    @Test
    void WhenGetCoordinatesFromCity_ThenCoordinatesGetReturned() {
        Coordinates expected = new Coordinates(0, 1, "Hungary", "Bács-Kiskun");

        Coordinates actual = geoCodeAPIFetcher.getCoordinatesFrom("asd");

        assertEquals(expected, actual);
    }

    @Test
    void WhenCityIsNull_ThenInvalidCityExceptionIsThrown() {

        assertThrows(CityNotFoundException.class, () -> this.geoCodeAPIFetcher
                .getCoordinatesFrom(null));
    }

    @Test
    void getCoordinatesFromCityThrowsInvalidCityExceptionForInvalidCityName() {
        String invalidCityName = "123123123";
        /*when(coordinateFetcher.getCoordinatesForCity(invalidCityName))
                .thenReturn(Mono.just(null));*/

        assertThrows(CityNotFoundException.class, () -> this.geoCodeAPIFetcher
                .getCoordinatesFrom(invalidCityName));
    }
}