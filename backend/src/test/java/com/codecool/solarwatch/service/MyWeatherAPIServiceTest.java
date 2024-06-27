package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.weather.current_weather_response.service.GeoCodeAPIService;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.NotSupportedCityException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@DisplayName("OpenWeatherService Unit test")
@ExtendWith(MockitoExtension.class)
class MyWeatherAPIServiceTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;
    @Mock
    private GeoCodeAPIService geoCodeAPIService;
    @InjectMocks
    MyWeatherAPIService myWeatherAPIService;

    @Test
    void WhenProvidedInvalidDateParameter_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
        assertThrows(InvalidDateException.class,
                () -> this.myWeatherAPIService.getSunriseSunsetInfo("asd", "asd"));
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
        SunriseSunsetInfo actualSunriseSunsetInfo = this.myWeatherAPIService.getSunriseSunsetInfo(cityName, null);

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

        SunriseSunsetInfo actual = this.myWeatherAPIService.getSunriseSunsetInfo(cityName, "1996-09-17");

        assertEquals(expectedSunriseSunsetInfo, actual);
    }

    @Test
    void getSunriseSunsetInfo_ThrowsInvalidCityException_WhenProvidedInvalidCityName() {
        when(this.cityRepository.findByName("asd")).thenThrow(new NotSupportedCityException("asd"));

        assertThrows(NotSupportedCityException.class,
                () -> this.myWeatherAPIService.getSunriseSunsetInfo("asd", "2024-06-16"));
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