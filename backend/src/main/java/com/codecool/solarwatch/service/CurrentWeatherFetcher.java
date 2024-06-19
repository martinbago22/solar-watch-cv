package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.Coordinates;
import com.codecool.solarwatch.model.SolarResultDetails;
import com.codecool.solarwatch.model.WeatherReport;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class CurrentWeatherFetcher {
    private static final String API = System.getenv("API_KEY");
    private final WebClient webClient;
    private final GeoCodeService geoCodeService;

    public CurrentWeatherFetcher(WebClient webClient, GeoCodeService geoCodeService) {
        this.webClient = webClient;
        this.geoCodeService = geoCodeService;
    }
}
