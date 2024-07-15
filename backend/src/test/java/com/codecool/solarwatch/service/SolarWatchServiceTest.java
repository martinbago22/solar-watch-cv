package com.codecool.solarwatch.service;

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
                String expectedErrorMessage = "Date is in invalid format. Please provide it with YYYY-MM-DD";

                InvalidDateException exception = assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, invalidDate));


                verify(cityRepository, times(0)).findByName(cityName);
                assertEquals(expectedErrorMessage, exception.getMessage());
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided not existing date")
            void WhenGivenNonExistingDate_ThenGetSunriseSunsetExceptionThrowsInvalidDateException() {
                String invalidDate = "2023-04-40";
                String cityName = "asd";
                String expectedErrorMessage = "Date is in invalid format. Please provide it with YYYY-MM-DD";

                InvalidDateException exception = assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, invalidDate));

                verify(cityRepository, times(0)).findByName(cityName);
                assertEquals(expectedErrorMessage, exception.getMessage());
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided null as date")
            void WhenGivenNullAsDate_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
                String cityName = "asd";
                String expectedErrorMessage = "date cannot be null or empty";

                InvalidDateException exception = assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, null));

                verify(cityRepository, times(0)).findByName(cityName);
                assertEquals(expectedErrorMessage, exception.getMessage());
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws invalid date exception when provided empty string as date")
            void WhenGivenEmptyStringAsDate_ThenGetSunriseSunsetInfoThrowsInvalidDateException() {
                String cityName = "asd";
                String emptyDate = "";
                String expectedErrorMessage = "date cannot be null or empty";

                InvalidDateException exception = assertThrows(InvalidDateException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(cityName, emptyDate));

                verify(cityRepository, times(0)).findByName(cityName);
                assertEquals(expectedErrorMessage, exception.getMessage());
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws city not found exception when provided not existing city")
            void WhenGivenNotExistingCity_ThenGetSunriseSunsetInfoThrowsCityNotFoundException() {
                String nonExistingCity = "nonexistent";
                String validDate = "2023-04-12";
                String expectedErrorMessage = "No city found under name: nonexistent";
                CityNotFoundException expectedException = new CityNotFoundException(nonExistingCity);

                when(cityRepository.findByName(nonExistingCity))
                        .thenThrow(expectedException);
                when(geoCodeAPIFetcher.getCoordinatesFromCityName(nonExistingCity))
                        .thenThrow(expectedException);


                CityNotFoundException exception = assertThrows(CityNotFoundException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(nonExistingCity, validDate));
                verify(cityRepository, times(1)).findByName(nonExistingCity);
                verify(geoCodeAPIFetcher, times(1)).getCoordinatesFromCityName(nonExistingCity);
                verify(cityRepository, times(0)).save(any(City.class));
                assertEquals(expectedErrorMessage, exception.getMessage());
            }

            @Test
            @DisplayName("getSunriseSunsetInfo throws city not found exception when provided empty string")
            void WhenGivenEmptyStringAsCity_ThenGetSunriseSunsetInfoThrowsCityNotFoundException() {
                String emptyString = "";
                String validDate = "2023-04-12";
                String expectedErrorMessage = "No city found under name: ";
                CityNotFoundException expectedException = new CityNotFoundException(emptyString);

                when(cityRepository.findByName(emptyString))
                        .thenThrow(expectedException);
                when(geoCodeAPIFetcher.getCoordinatesFromCityName(emptyString))
                        .thenThrow(expectedException);

                CityNotFoundException exception = assertThrows(CityNotFoundException.class,
                        () -> solarWatchService.getSunriseSunsetInfo(emptyString, validDate));
                verify(cityRepository, times(1)).findByName(emptyString);
                verify(geoCodeAPIFetcher, times(1)).getCoordinatesFromCityName(emptyString);
                verify(cityRepository, times(0)).save(any(City.class));
                assertEquals(expectedErrorMessage, exception.getMessage());
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
                verify(geoCodeAPIFetcher, times(0)).getCoordinatesFromCityName(validCityName);
                verify(cityRepository, never()).save(validCity);
            }

            @Test
            @DisplayName("getSunriseSunsetInfo fetches requested info from API if it's not in DB already")
            void WhenGivenValidParameters_ThenGetSunriseSunsetInfoFetchesFromAPIIfNotInDBAlready() {
                when(cityRepository.findByName(validCityName))
                        .thenThrow(CityNotFoundException.class);
                when(geoCodeAPIFetcher.getCoordinatesFromCityName(validCityName))
                        .thenReturn(validCoordinates);
                when(sunriseSunsetRepository.getSunriseSunsetByCityAndDate(validCity, validDate))
                        .thenThrow(SunriseSunsetNotFoundException.class);
                when(currentWeatherAPIFetcher.fetchSunriseSunsetInfo(validCity, validDate))
                        .thenReturn(expectedInfo);

                SunriseSunsetInfo actualInfo = solarWatchService.getSunriseSunsetInfo(validCityName, validDateString);
                verify(cityRepository, times(1)).findByName(validCityName);
                verify(geoCodeAPIFetcher, times(1)).getCoordinatesFromCityName(validCityName);
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
                    verify(geoCodeAPIFetcher, never()).getCoordinatesFromCityName(null);
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
                    verify(geoCodeAPIFetcher, never()).getCoordinatesFromCityName(emptyString);
                    assertEquals(expectedErrorMessage, exception.getMessage());
                }
            }
        }
    }
}