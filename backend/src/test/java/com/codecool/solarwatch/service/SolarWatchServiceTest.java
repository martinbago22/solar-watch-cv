package com.codecool.solarwatch.service;

import com.codecool.solarwatch.api.current_weather.model.*;
import com.codecool.solarwatch.api.current_weather.service.CurrentWeatherAPIFetcher;
import com.codecool.solarwatch.api.geocoding.model.Coordinates;
import com.codecool.solarwatch.api.geocoding.service.GeoCodeAPIFetcher;
import com.codecool.solarwatch.exception.CityNotFoundException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.SunriseSunsetNotFoundException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunriseSunsetInfo;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

@DisplayName("SolarWatchService Unit test")
@ExtendWith(MockitoExtension.class)
class SolarWatchServiceTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;
    @Mock
    private GeoCodeAPIFetcher geoCodeAPIFetcher;
    @Mock
    private CurrentWeatherAPIFetcher currentWeatherAPIFetcher;
    @InjectMocks
    SolarWatchService solarWatchService;

    @Nested
    @DisplayName("getSunriseSunsetInfo method tests")
    class GetSunriseSunsetInfoTests {
        @Nested
        @DisplayName("getSunriseSunsetInfo fails")
        class GetSunriseSunsetInfoFails {
            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided an invalid date")
            void WhenGivenInvalidDate_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
                String invalidDate = "asd";
                String cityName = "asd";

                assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, invalidDate));

                verify(cityRepository, times(0)).findByName(cityName);
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided not existing date")
            void WhenGivenNonExistingDate_ThenGetSunriseSunsetExceptionThrowsInvalidDateException() {
                String invalidDate = "2023-04-40";
                String cityName = "asd";

                assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, invalidDate));
                verify(cityRepository, times(0)).findByName(cityName);
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided null as date")
            void WhenGivenNullAsDate_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
                String cityName = "asd";

                assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, null));

                verify(cityRepository, times(0)).findByName(cityName);
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided empty string as date")
            void WhenGivenEmptyStringAsDate_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
                String cityName = "asd";
                String emptyDate = "";

                assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, emptyDate));

                verify(cityRepository, times(0)).findByName(cityName);
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws city not found exception when provided not existing city")
            void WhenGivenNotExistingCity_ThenGetSunriseSunsetInfoThrowsCityNotFoundException() {
                String nonExistingCity = "nonexistent";
                String validDate = "2023-04-12";

                when(cityRepository.findByName(nonExistingCity))
                        .thenThrow(CityNotFoundException.class);
                when(geoCodeAPIFetcher.getCoordinatesFrom(nonExistingCity))
                        .thenThrow(CityNotFoundException.class);


                assertThrows(CityNotFoundException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(nonExistingCity, validDate));
                verify(cityRepository, times(1)).findByName(nonExistingCity);
                verify(geoCodeAPIFetcher, times(1)).getCoordinatesFrom(nonExistingCity);
                verify(cityRepository, times(0)).save(any(City.class));
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws city not found exception when provided empty string")
            void WhenGivenEmptyStringAsCity_ThenGetSunriseSunsetInfoThrowsCityNotFoundException() {
                String emptyString = "";
                String validDate = "2023-04-12";

                when(cityRepository.findByName(emptyString))
                        .thenThrow(CityNotFoundException.class);
                when(geoCodeAPIFetcher.getCoordinatesFrom(emptyString))
                        .thenThrow(CityNotFoundException.class);

                assertThrows(CityNotFoundException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(emptyString, validDate));
                verify(cityRepository, times(1)).findByName(emptyString);
                verify(geoCodeAPIFetcher, times(1)).getCoordinatesFrom(emptyString);
                verify(cityRepository, times(0)).save(any(City.class));
            }
        }

        @Nested
        @DisplayName("getSunriseSunsetInfo succeeds")
        class GetSunriseSunsetInfoSucceeds {
            Coordinates validCoordinates = new Coordinates(12, 12, "asd", "asd");
            String validCityName = "Budapest";
            String validDateString = "2024-07-10";
            City validCity = new City("Budapest", validCoordinates);
            LocalDate validDate = LocalDate.of(2024, 7, 10);
            SunriseSunsetInfo expectedInfo = new SunriseSunsetInfo(validDate, LocalTime.now(),
                    LocalTime.now(), validCity);

            @Test
            @DisplayName("getSunriseSunsetInfo gets requested information from DB if it already exists there")
            void WhenGivenValidParameters_ThenGetSunriseSunsetInfoReturnsRequestedInformationFromDatabaseIfItExistsThere() {
                when(cityRepository.findByName(validCityName))
                        .thenReturn(Optional.of(validCity));
                when(sunriseSunsetRepository.getSunriseSunsetByCityAndDate(validCity, validDate))
                        .thenReturn(Optional.of(expectedInfo));

                SunriseSunsetInfo actualInfo = solarWatchService.getSunriseSunsetInfo(validCityName, validDateString);

                assertEquals(expectedInfo, actualInfo);
                verify(cityRepository, times(1)).findByName(validCityName);
                verify(sunriseSunsetRepository, times(1)).getSunriseSunsetByCityAndDate(validCity, validDate);
                verify(geoCodeAPIFetcher, times(0)).getCoordinatesFrom(validCityName);
                verify(cityRepository, never()).save(validCity);
            }

            @Test
            @DisplayName("getSunriseSunsetInfo fetches requested info from API if it's not in DB already")
            void WhenGivenValidParameters_ThenGetSunriseSunsetInfoFetchesFromAPIIfNotInDBAlready() {
                when(cityRepository.findByName(validCityName))
                        .thenThrow(CityNotFoundException.class);
                when(geoCodeAPIFetcher.getCoordinatesFrom(validCityName))
                        .thenReturn(validCoordinates);
                when(sunriseSunsetRepository.getSunriseSunsetByCityAndDate(validCity, validDate))
                        .thenThrow(SunriseSunsetNotFoundException.class);
                when(currentWeatherAPIFetcher.fetchSunriseSunsetInfo(validCity, validDate))
                        .thenReturn(expectedInfo);

                SunriseSunsetInfo actualInfo = solarWatchService.getSunriseSunsetInfo(validCityName, validDateString);
                verify(cityRepository, times(1)).findByName(validCityName);
                verify(geoCodeAPIFetcher, times(1)).getCoordinatesFrom(validCityName);
                verify(sunriseSunsetRepository, times(1)).getSunriseSunsetByCityAndDate(validCity, validDate);
                verify(currentWeatherAPIFetcher, times(1)).fetchSunriseSunsetInfo(validCity, validDate);
                verify(cityRepository, times(1)).save(validCity);
                assertEquals(expectedInfo, actualInfo);
            }
        }

        @Nested
        @DisplayName("getCurrentWeatherResponseFor method test")
        class GetCurrentWeatherResponseForMethodTests {
            @Nested
            @DisplayName("getCurrentWeatherResponseFor fails")
            class GetCurrentWeatherResponseForFails {
                @Test
                @DisplayName("getCurrentWeatherResponseFor throws CityNotFoundException when given null")
                void WhenGivenNull_ThenGetCurrentResponseForThrowsCityNotFoundException() {
                    CityNotFoundException exception = assertThrows(CityNotFoundException.class,
                            () -> solarWatchService.getCurrentWeatherResponseFor(null));
                    String expectedErrorMessage = "No city found under name: null";

                    verify(currentWeatherAPIFetcher, never()).fetchCurrentWeatherResponse(null);
                    verify(geoCodeAPIFetcher, never()).getCoordinatesFrom(null);
                    assertEquals(expectedErrorMessage, exception.getMessage());
                }

                @Test
                @DisplayName("getCurrentWeatherResponseFor throws CityNotFoundException when given empty string")
                void WhenGivenEmptyString_ThenGetCurrentResponseForThrowsCityNotFoundException() {
                    String emptyString = "";
                    CityNotFoundException exception = assertThrows(CityNotFoundException.class,
                            () -> solarWatchService.getCurrentWeatherResponseFor(emptyString));
                    String expectedErrorMessage = "No city found under name: ";


                    verify(currentWeatherAPIFetcher, never()).fetchCurrentWeatherResponse(any(Coordinates.class));
                    verify(geoCodeAPIFetcher, never()).getCoordinatesFrom(emptyString);
                    assertEquals(expectedErrorMessage, exception.getMessage());
                }

                @Test
                @DisplayName("getCurrentWeatherResponseFor throws CityNotFoundException when given not existing cityName")
                void WhenGivenNotExistingCityName_ThenGetCurrentResponseForThrowsCityNotFoundException() {
                    String notExistingCityName = "qwe";

                    when(geoCodeAPIFetcher.getCoordinatesFrom(notExistingCityName))
                            .thenThrow(CityNotFoundException.class);


                    assertThrows(CityNotFoundException.class,
                            () -> solarWatchService.getCurrentWeatherResponseFor(notExistingCityName));
                    verify(currentWeatherAPIFetcher, never()).fetchCurrentWeatherResponse(any(Coordinates.class));
                }
            }

            @Nested
            @DisplayName("getCurrentWeatherResponseFor succeeds")
            class GetCurrentWeatherResponseForSucceeds {
                @Test
                @DisplayName("getCurrentWeatherResponseSuceeds when given valid city name")
                void WhenGivenValidCityName_ThenGetCurrentWeatherResponseForSucceeds() {
                    CurrentWeatherResponse expectedCWR = new CurrentWeatherResponse(new CurrentWeatherDescription[]{new CurrentWeatherDescription("asd")},
                            new MainWeatherInfo(12, 12),
                            new WindInfo(12),
                            12,
                            new CurrentWeatherAPISunriseSunsetTime("12", "12"));
                    String validCityName = "Budapest";
                    Coordinates validCoordinates = new Coordinates(12, 12, "asd", "asd");

                    when(geoCodeAPIFetcher.getCoordinatesFrom(validCityName))
                            .thenReturn(validCoordinates);
                    when(currentWeatherAPIFetcher.fetchCurrentWeatherResponse(validCoordinates))
                            .thenReturn(expectedCWR);

                    CurrentWeatherResponse actualCWR = solarWatchService.getCurrentWeatherResponseFor(validCityName);
                    verify(geoCodeAPIFetcher, times(1)).getCoordinatesFrom(validCityName);
                    verify(currentWeatherAPIFetcher, times(1)).fetchCurrentWeatherResponse(validCoordinates);
                    assertEquals(expectedCWR, actualCWR);
                }

            }
        }
    }
}