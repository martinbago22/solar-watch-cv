package com.codecool.solarwatch.api.weather.current_weather_response.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.model.Coordinates;
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
public class GeoCodeAPIService {
    private static final Logger logger = LoggerFactory.getLogger(GeoCodeAPIService.class);
    private static final String API_KEY = System.getenv("API_KEY");
    private final WebClient webClient;

    @Autowired
    public GeoCodeAPIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Coordinates getCoordinatesFromCity(@NotBlank(message = "City parameter cannot be empty or null") String city) {
        Coordinates coordinates = getCoordinatesForCity(city);
        if (coordinates != null) {
            return coordinates;
        } else {
            logger.error("City parameter cannot be empty or null");
            throw new InvalidCityException(city);
        }
    }

    private Coordinates getCoordinatesForCity(String cityName) {
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
                        return Mono.empty();
                    }
                })
                .block();
    }
}
