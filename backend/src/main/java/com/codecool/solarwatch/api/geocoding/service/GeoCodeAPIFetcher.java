package com.codecool.solarwatch.api.geocoding.service;

import com.codecool.solarwatch.api.geocoding.model.Coordinates;
import com.codecool.solarwatch.exception.CityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GeoCodeAPIFetcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCodeAPIFetcher.class);
    private static final String API_KEY = System.getenv("API_KEY");
    private final WebClient webClient;

    @Autowired
    public GeoCodeAPIFetcher(WebClient webClient) {
        this.webClient = webClient;
    }

    public Coordinates getCoordinatesFrom(@NotBlank(message = "City parameter cannot be empty or null") String cityName) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                cityName, API_KEY);

        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Coordinates>>() {
                })
                .flatMap(coordinatesList -> {
                    if (coordinatesList != null && !coordinatesList.isEmpty()) {
                        return Mono.just(coordinatesList.getFirst());
                    } else {
                        LOGGER.error("No city found under name: [{}]", cityName);
                        return Mono.error(new CityNotFoundException(cityName));
                    }
                })
                .block();
    }
}
