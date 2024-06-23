package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.weather.current_weather_response.service.CoordinateFetcher;
import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class GeoCodeService {
    private final CoordinateFetcher coordinateFetcher;

    public GeoCodeService(CoordinateFetcher coordinateFetcher) {
        this.coordinateFetcher = coordinateFetcher;
    }

    public Coordinates getCoordinatesFromCity(String city) {
        Coordinates[] cities = coordinateFetcher.getCoordinatesForCity(city);
        if (cities != null && cities.length > 0) {
            return cities[0];
        } else {
            throw new InvalidCityException(city);
        }
    }
}
