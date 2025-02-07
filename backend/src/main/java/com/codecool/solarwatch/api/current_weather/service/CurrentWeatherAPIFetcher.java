package com.codecool.solarwatch.api.current_weather.service;

import com.codecool.solarwatch.api.current_weather.model.CurrentWeatherResponse;
import com.codecool.solarwatch.api.current_weather.model.SolarResultDetails;
import com.codecool.solarwatch.api.current_weather.model.WeatherReport;
import com.codecool.solarwatch.api.geocoding.model.Coordinates;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.codecool.solarwatch.util.Utility.converToLocalTime;

@Component
public class CurrentWeatherAPIFetcher {
    private final WebClient webClient;

    @Autowired
    public CurrentWeatherAPIFetcher(WebClient webClient) {
        this.webClient = webClient;
    }

    public SunriseSunsetInfo fetchSunriseSunsetInfo(City city, LocalDate date) {
        SunriseSunsetInfo sunriseSunsetInfo;

        Coordinates coordinates = new Coordinates(city.getLatitude(),
                city.getLongitude(),
                city.getCountry(),
                city.getState());
        SolarResultDetails solarResultDetails = getSolarResultDetails(coordinates, date.toString());
        sunriseSunsetInfo = new SunriseSunsetInfo(date,
                converToLocalTime(solarResultDetails.sunrise()),
                converToLocalTime(solarResultDetails.sunset()),
                city);
        return sunriseSunsetInfo;
    }

    public CurrentWeatherResponse fetchCurrentWeatherResponse(Coordinates coordinates) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",
                coordinates.latitude(), coordinates.longitude(), System.getenv("API_KEY"));

        return this.webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(CurrentWeatherResponse.class)
                .block();
    }

    private SolarResultDetails getSolarResultDetails(Coordinates coordinates, String date) {
        String url = "https://api.sunrise-sunset.org/json?";
        if (isDateCorrectFormat(date))
            url += String.format("lat=%s&lng=%s&date=%s&tzid=Europe/Budapest",
                    coordinates.latitude(),
                    coordinates.longitude(),
                    date);
        else {
            throw new InvalidDateException();
        }
        WeatherReport weatherReport = getWeatherReportFrom(url);
        return getSolarResultDetailsFrom(weatherReport);
    }

    private boolean isDateCorrectFormat(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private WeatherReport getWeatherReportFrom(String url) {
        return this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherReport.class)
                .block();
    }

    private SolarResultDetails getSolarResultDetailsFrom(WeatherReport weatherReport) {
        return new SolarResultDetails(weatherReport.results().sunrise(),
                weatherReport.results().sunset(),
                weatherReport.results().date());
    }

}
