package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.api_response.Coordinates;
import com.codecool.solarwatch.model.api_response.current_weather_response.CurrentWeatherResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CurrentWeatherFetcher {
    private static final String API_KEY = System.getenv("API_KEY");
    private final WebClient webClient;
    private final GeoCodeService geoCodeService;

    public CurrentWeatherFetcher(WebClient webClient, GeoCodeService geoCodeService) {
        this.webClient = webClient;
        this.geoCodeService = geoCodeService;
    }
}
