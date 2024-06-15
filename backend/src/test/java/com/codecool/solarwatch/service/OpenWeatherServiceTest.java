package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunset;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherServiceTest {
    private static final String API = "https://api.sunrise-sunset.org/json?";
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherServiceTest.class);
    @Mock
    private WebClient webClient;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;
    @Mock
    private GeoCodeService geoCodeService;
    @InjectMocks
    OpenWeatherService openWeatherService;

    @Test
    void getSunriseSunsetThrowsInvalidDateExceptionWhenProvidedInvalidDateParameter() {
        assertThrows(InvalidDateException.class,
                () -> this.openWeatherService.getSunriseSunset("asd", "asd"));
    }

    @Test
    void getSunriseSunsetGetsSunriseSunsetInfoOfCurrentDateWhenNoDateParameterProvided() {
        String cityName = "Budapest";
        City mockCity = new City("Budapest",
                12,
                12,
                "Asd",
                "asd");
        LocalDate dateWhenNotProvided = LocalDate.now();
        SunriseSunset expectedSunriseSunset = new SunriseSunset(dateWhenNotProvided,
                LocalTime.now(),
                LocalTime.now(),
                mockCity);

        when(this.cityRepository.findByName(cityName))
                .thenReturn(Optional.of(mockCity));
        when(this.sunriseSunsetRepository.getSunriseSunsetByCityAndDate(mockCity, dateWhenNotProvided))
                .thenReturn(Optional.of(expectedSunriseSunset));
        SunriseSunset actualSunriseSunset = this.openWeatherService.getSunriseSunset(cityName, null);

        assertEquals(expectedSunriseSunset, actualSunriseSunset);
    }
}