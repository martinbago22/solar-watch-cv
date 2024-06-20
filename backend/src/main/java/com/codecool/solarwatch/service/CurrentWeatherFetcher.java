package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.Coordinates;
import com.codecool.solarwatch.model.SolarResultDetails;
import com.codecool.solarwatch.model.WeatherReport;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

import static com.codecool.solarwatch.util.Utility.converToLocalTime;

@Component
public class CurrentWeatherFetcher {
    private static final String API = System.getenv("API_KEY");
    private final WebClient webClient;
    private final GeoCodeService geoCodeService;

    public CurrentWeatherFetcher(WebClient webClient, GeoCodeService geoCodeService) {
        this.webClient = webClient;
        this.geoCodeService = geoCodeService;
    }

    private SunriseSunsetInfo fetchSunriseSunsetInfo(City city, LocalDate date) {
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
}
