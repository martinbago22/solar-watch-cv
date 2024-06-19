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

    private SolarResultDetails getSolarResultDetails(Coordinates coordinates, String date) {
        String url;
        if (isDateProvided(date)) {
            if (isDateCorrectFormat(date))
                url = String.format(API + "lat=%s&lng=%s&date=%s",
                        coordinates.latitude(),
                        coordinates.longitude(),
                        date);
            else {
                throw new InvalidDateException();
            }
        } else {
            url = String.format(API + "lat=%s&lng=%s", coordinates.latitude(), coordinates.longitude());
        }
        WeatherReport weatherReport = getWeatherReportFrom(url);
        return getSolarResultDetailsFrom(weatherReport);
    }
    private boolean isDateProvided(String date) {
        return date != null && !date.trim().isEmpty();
    }

    private boolean isDateCorrectFormat(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
