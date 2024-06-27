package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.weather.current_weather_response.model.CurrentWeatherResponse;
import com.codecool.solarwatch.api.weather.current_weather_response.service.CurrentWeatherAPIFetcher;
import com.codecool.solarwatch.api.weather.current_weather_response.service.GeoCodeAPIService;
import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.NotSupportedCityException;
import com.codecool.solarwatch.exception.SunriseSunsetNotFoundException;
import com.codecool.solarwatch.model.Coordinates;
import com.codecool.solarwatch.model.dto.CurrentWeatherInfoDTO;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codecool.solarwatch.util.Utility.convertToCelsius;
import static com.codecool.solarwatch.util.Utility.convertUnixUTCToLocalDateTime;

@Service
public class MyWeatherAPIService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyWeatherAPIService.class);
    private final CityRepository cityRepository;
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private final GeoCodeAPIService geoCodeAPIService;
    private final CurrentWeatherAPIFetcher currentWeatherAPIFetcher;

    @Autowired
    public MyWeatherAPIService(CityRepository cityRepository,
                               SunriseSunsetRepository sunriseSunsetRepository,
                               GeoCodeAPIService geoCodeAPIService,
                               CurrentWeatherAPIFetcher currentWeatherAPIFetcher) {
        this.cityRepository = cityRepository;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
        this.geoCodeAPIService = geoCodeAPIService;
        this.currentWeatherAPIFetcher = currentWeatherAPIFetcher;
    }

    public SunriseSunsetInfo getSunriseSunsetInfo(String cityName, String date) {
        LocalDate parsedDate = handleDateParameter(date);
        City city = getCityFromDatabaseOrFetch(cityName);
        SunriseSunsetInfo sunriseSunsetInfo;
        try {
            sunriseSunsetInfo = getSunriseSunsetInfoByCityAndDate(city, parsedDate);
        } catch (SunriseSunsetNotFoundException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("Fetching sunset/sunrise time from api and saving to Database");
            sunriseSunsetInfo = currentWeatherAPIFetcher.fetchSunriseSunsetInfo(city, parsedDate);
            sunriseSunsetRepository.save(sunriseSunsetInfo);

        }
        return sunriseSunsetInfo;
    }

    private City getCityByName(String cityName) {
        return cityRepository
                .findByName(cityName)
                .orElseThrow(() -> new InvalidCityException(cityName));
    }

    private SunriseSunsetInfo getSunriseSunsetInfoByCityAndDate(City city, LocalDate date) {
        return this.sunriseSunsetRepository
                .getSunriseSunsetByCityAndDate(city, date)
                .orElseThrow(() -> new SunriseSunsetNotFoundException(city, date));
    }

    private LocalDate handleDateParameter(String date) {
        if (date == null || date.isEmpty()) {
            throw new InvalidDateException("date cannot be null or empty");
        }
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }

    private City getCityFromDatabaseOrFetch(String cityName) {
        City city;
        try {
            city = getCityByName(cityName);
        } catch (InvalidCityException e) {
            city = saveCityEntityIfNotInDatabase(cityName);
        }
        return city;
    }


    private City saveCityEntityIfNotInDatabase(String cityName) {
        Coordinates coordinates = this.geoCodeAPIService.getCoordinatesFromCity(cityName);
        City city = new City(cityName,
                coordinates);
        LOGGER.info(String.format("%s successfully saved to DB", city));
        return this.cityRepository.save(city);
    }


    @Transactional
    public void deleteCityByName(String cityName) {
        try {
            City requestedCity = getCityByName(cityName);
            this.cityRepository.delete(requestedCity);
            LOGGER.info("City [{}] deleted from database", cityName);
        } catch (InvalidCityException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Transactional
    public void updateCityInfo(String cityName) {
        City requestedCity;
        try {
            requestedCity = getCityByName(cityName);
            this.cityRepository.save(requestedCity);
            LOGGER.info("City: [{}] successfully updated", requestedCity.getName());
        } catch (NotSupportedCityException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public CurrentWeatherResponse getCurrentWeatherResponseFor(String cityName) {
        Coordinates coordinates = this.geoCodeAPIService.getCoordinatesFromCity(cityName);
        if (cityName != null && !cityName.trim().isEmpty()) {
            return this.currentWeatherAPIFetcher
                    .fetchCurrentWeatherResponse(coordinates);
        } else {
            throw new InvalidCityException(cityName);
        }
    }

    public CurrentWeatherInfoDTO getCurrentWeatherInfoDTOFrom(CurrentWeatherResponse currentWeatherResponse, String cityName) {
        convertUnixUTCToLocalDateTime(currentWeatherResponse.currentWeatherAPISunriseSunsetTime().sunriseUnixUTC());
        return new CurrentWeatherInfoDTO(cityName,
                convertToCelsius(currentWeatherResponse.mainWeatherInfo().temperature()),
                currentWeatherResponse.mainWeatherInfo().humidity(),
                currentWeatherResponse.windInfo().speed(),
                currentWeatherResponse.currentWeatherDescription()[0].description(),
                currentWeatherResponse.visibility(),
                provideWarningMessageAboutCloudyWeather(currentWeatherResponse),
                currentWeatherResponse.currentWeatherAPISunriseSunsetTime().sunriseUnixUTC(),
                currentWeatherResponse.currentWeatherAPISunriseSunsetTime().sunsetUnixUTC());
    }

    private String provideWarningMessageAboutCloudyWeather(CurrentWeatherResponse currentWeatherResponse) {
        boolean isCloudyWeather = isCloudyWeather(currentWeatherResponse);
        return isCloudyWeather ? "Weather seems to be cloudy and not ideal for taking pictures" :
                "Weather seems to be ideal for taking pictures";
    }

    private boolean isCloudyWeather(CurrentWeatherResponse currentWeatherResponse) {
        Pattern p = Pattern.compile("cloud", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(currentWeatherResponse.currentWeatherDescription()[0].description());
        return m.find();
    }
}
