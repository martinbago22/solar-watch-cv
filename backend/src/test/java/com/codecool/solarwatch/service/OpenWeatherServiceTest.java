package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.NotSupportedCityException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
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
import static org.mockito.Mockito.*;

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
    void getSunriseSunsetInfo_ThrowsInvalidDateException_WhenProvidedInvalidDateParameter() {
        assertThrows(InvalidDateException.class,
                () -> this.openWeatherService.getSunriseSunsetInfo("asd", "asd"));
    }

    @Test
    void getSunriseSunsetInfo_GetsSunriseSunsetInfoOfCurrentDate_WhenNoDateParameterProvided() {
        String cityName = "Budapest";
        City mockCity = new City(cityName,
                12,
                12,
                "Asd",
                "asd");
        LocalDate dateWhenNotProvided = LocalDate.now();
        SunriseSunsetInfo expectedSunriseSunsetInfo = new SunriseSunsetInfo(dateWhenNotProvided,
                LocalTime.now(),
                LocalTime.now(),
                mockCity);

        when(this.cityRepository.findByName(cityName))
                .thenReturn(Optional.of(mockCity));
        when(this.sunriseSunsetRepository.getSunriseSunsetByCityAndDate(mockCity, dateWhenNotProvided))
                .thenReturn(Optional.of(expectedSunriseSunsetInfo));
        SunriseSunsetInfo actualSunriseSunsetInfo = this.openWeatherService.getSunriseSunsetInfo(cityName, null);

        assertEquals(expectedSunriseSunsetInfo, actualSunriseSunsetInfo);
    }

    @Test
    void getSunriseSunsetInfo_GetsSunriseSunsetOfProvidedDate_WhenProvidedValidParameters() {
        String cityName = "Budapest";
        LocalDate requestedDate = LocalDate.of(1996, 9, 17);
        City mockCity = new City(cityName,
                12,
                12,
                "Alabama",
                "USA");
        SunriseSunsetInfo expectedSunriseSunsetInfo = new SunriseSunsetInfo(requestedDate,
                LocalTime.now(),
                LocalTime.now(),
                mockCity);

        when(this.cityRepository.findByName(cityName))
                .thenReturn(Optional.of(mockCity));
        when(this.sunriseSunsetRepository.getSunriseSunsetByCityAndDate(mockCity, requestedDate))
                .thenReturn(Optional.of(expectedSunriseSunsetInfo));

        SunriseSunsetInfo actual = this.openWeatherService.getSunriseSunsetInfo(cityName, "1996-09-17");

        assertEquals(expectedSunriseSunsetInfo, actual);
    }

    @Test
    void getSunriseSunsetInfo_ThrowsInvalidCityException_WhenProvidedInvalidCityName() {
        when(this.cityRepository.findByName("asd")).thenThrow(new NotSupportedCityException("asd"));

        assertThrows(NotSupportedCityException.class,
                () -> this.openWeatherService.getSunriseSunsetInfo("asd", "2024-06-16"));
    }
    @Test
    void deleteCityByName_DeletesRequestedCity_WhenItExistsInDatabase() {
        City mockCity = mock(City.class);
        mockCity.setName("Budapest");
        this.cityRepository.save(mockCity);

        when(this.cityRepository.findByName(mockCity.getName()))
                .thenReturn(Optional.of(mockCity));

    }
}