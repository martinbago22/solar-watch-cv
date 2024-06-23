package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.weather.current_weather_response.service.CoordinateFetcher;
import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.Coordinates;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GeoCodeService {
    private static final Logger logger = LoggerFactory.getLogger(GeoCodeService.class);
    private final CoordinateFetcher coordinateFetcher;

    public GeoCodeService(CoordinateFetcher coordinateFetcher) {
        this.coordinateFetcher = coordinateFetcher;
    }

    public Coordinates getCoordinatesFromCity(@NotBlank(message = "City parameter cannot be empty or null" ) String city) {
        Coordinates coordinates = coordinateFetcher.getCoordinatesForCity(city);
        if (coordinates != null) {
            return coordinates;
        } else {
            logger.error("City parameter cannot be empty or null");
            throw new InvalidCityException(city);
        }
    }
}
