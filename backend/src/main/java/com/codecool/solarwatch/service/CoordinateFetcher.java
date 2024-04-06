package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.Coordinates;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CoordinateFetcher {
    private static final String API_KEY = System.getenv("API_KEY");
    private final WebClient webClient;

    public CoordinateFetcher(WebClient webClient) {
        this.webClient = webClient;
    }

    public Coordinates[] getCoordinatesForCity(String cityName) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                cityName, API_KEY);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Coordinates[].class)
                .block();
    }
}
