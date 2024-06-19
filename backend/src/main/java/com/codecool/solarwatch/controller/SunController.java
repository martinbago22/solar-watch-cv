package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.api.weather.current_weather_response.CurrentWeatherResponse;
import com.codecool.solarwatch.model.dto.CurrentWeatherInfoDTO;
import com.codecool.solarwatch.model.dto.SunriseSunsetDTO;
import com.codecool.solarwatch.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/weatherforecast")
public class SunController {
    private final OpenWeatherService openWeatherService;

    @Autowired
    public SunController(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    @GetMapping("/suninfo")
    public ResponseEntity<SunriseSunsetDTO> getSunReport(@RequestParam(required = false) String date, @RequestParam(defaultValue = "Budapest") String city) {
        //TODO Logging
        SunriseSunsetDTO sunriseSunsetDTO = new SunriseSunsetDTO(openWeatherService.getSunriseSunsetInfo(city, date));
        return ResponseEntity.ok(sunriseSunsetDTO);
    }

    @GetMapping("/current")
    public ResponseEntity<CurrentWeatherInfoDTO> getCurrentWeatherInfoFrom(@RequestParam(defaultValue = "Budapest") String city) {
        CurrentWeatherResponse currentWeatherDetailsResponse =
                this.openWeatherService.getCurrentWeatherResponseFor(city);
        CurrentWeatherInfoDTO currentWeatherInfoDTO = this.openWeatherService.getCurrentWeatherInfoDTOFrom(currentWeatherDetailsResponse, city);
        return ResponseEntity.ok(currentWeatherInfoDTO);
    }

}
