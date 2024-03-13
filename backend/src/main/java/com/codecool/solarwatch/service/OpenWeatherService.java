package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.NotSupportedCityException;
import com.codecool.solarwatch.exception.SunriseSunsetNotFoundException;
import com.codecool.solarwatch.model.Coordinates;
import com.codecool.solarwatch.model.SolarResultDetails;
import com.codecool.solarwatch.model.WeatherReport;
import com.codecool.solarwatch.model.api_response.current_weather_response.CurrentWeatherResponse;
import com.codecool.solarwatch.model.dto.CurrentWeatherInfoDTO;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunset;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.codecool.solarwatch.util.Utility.*;

@Service
public class OpenWeatherService {
    private static final String API = "https://api.sunrise-sunset.org/json?";
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherService.class);
    private final WebClient webClient;
    private final CityRepository cityRepository;
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private final GeoCodeService geoCodeService;

    @Autowired
    public OpenWeatherService(WebClient webClient,
                              CityRepository cityRepository,
                              SunriseSunsetRepository sunriseSunsetRepository,
                              GeoCodeService geoCodeService) {
        this.webClient = webClient;
        this.cityRepository = cityRepository;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
        this.geoCodeService = geoCodeService;
    }

    private City getCityByName(String cityName) {
        return cityRepository
                .findByName(cityName)
                .orElseThrow(() -> new NotSupportedCityException(cityName));
    }

    private SunriseSunset getSunriseSunsetByCityAndDate(City city, LocalDate date) {
        return this.sunriseSunsetRepository
                .getSunriseSunsetByCityAndDate(city, date)
                .orElseThrow(() -> new SunriseSunsetNotFoundException(city, date));
    }

    public SunriseSunset getSunriseSunset(String cityName, String date) {
        SunriseSunset sunriseSunset;
        LocalDate parsedDate = LocalDate.now();
        if (date != null) {
            parsedDate = parseToLocalDate(date);
        }
        City city;
        try {
            city = getCityByName(cityName);
        } catch (NotSupportedCityException e) {
            city = createCityEntityIfNotInDatabase(cityName);
        }
        try {
            sunriseSunset = getSunriseSunsetByCityAndDate(city, parsedDate);
        } catch (SunriseSunsetNotFoundException e) {
            Coordinates coordinates = new Coordinates(city.getLatitude(),
                    city.getLongitude(),
                    city.getCountry(),
                    city.getState());
            SolarResultDetails solarResultDetails = getSolarResultDetails(coordinates, date);
            sunriseSunset = new SunriseSunset(parsedDate,
                    converToLocalTime(solarResultDetails.sunrise()),
                    converToLocalTime(solarResultDetails.sunset()),
                    city);
            sunriseSunset = this.sunriseSunsetRepository.save(sunriseSunset);
        }
        return sunriseSunset;
    }


    private City createCityEntityIfNotInDatabase(String cityName) {
        Coordinates coordinates = this.geoCodeService.getCoordinatesFromCity(cityName);
        City city = new City(cityName,
                coordinates.longitude(),
                coordinates.latitude(),
                coordinates.state(),
                coordinates.country());
        LOGGER.info(String.format("%s successfully saved to DB", city));
        return this.cityRepository.save(city);
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


    @Transactional
    public void deleteCityByName(String cityName) {
        City requestedCity = getCityByName(cityName);
        if (requestedCity == null) {
            throw new NotSupportedCityException(cityName);
        }
        this.cityRepository.delete(requestedCity);
    }

    public void updateCityInfo(String cityName) {
        City requestedCity;
        try {
            requestedCity = getCityByName(cityName);
        } catch (NotSupportedCityException e) {
            LOGGER.info(e.getMessage());
            Coordinates coordinates =
                    this.geoCodeService.getCoordinatesFromCity(cityName);
            requestedCity = new City(cityName,
                    coordinates.longitude(),
                    coordinates.latitude(),
                    coordinates.state(),
                    coordinates.country());
        }
        this.cityRepository.save(requestedCity);
    }

    public CurrentWeatherResponse getCurrentWeatherResponseFor(String cityName) {
        Coordinates coordinates = this.geoCodeService.getCoordinatesFromCity(cityName);
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",
                coordinates.latitude(), coordinates.longitude(), System.getenv("API_KEY"));
        if (cityName != null && !cityName.trim().isEmpty()) {
            return this.webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(CurrentWeatherResponse.class)
                    .block();
        } else {
            throw new InvalidCityException(cityName);
        }
    }

    public CurrentWeatherInfoDTO getCurrentWeatherInfoDTOFrom(CurrentWeatherResponse currentWeatherResponse, String cityName) {
        return new CurrentWeatherInfoDTO(cityName,
                convertToCelsius(currentWeatherResponse.mainWeatherInfo().temperature()),
                currentWeatherResponse.mainWeatherInfo().humidity(),
                currentWeatherResponse.windInfo().speed(),
                currentWeatherResponse.currentWeatherInfo()[0].description());
    }
}
