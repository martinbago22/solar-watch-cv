package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.CurrentWeatherInfoDTO;
import com.codecool.solarwatch.model.dto.SunriseSunsetDTO;
import com.codecool.solarwatch.model.api_response.current_weather_response.CurrentWeatherResponse;
import com.codecool.solarwatch.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/weatherforecast")
public class SunController {
    private final OpenWeatherService openWeatherService;

    @Autowired
    public SunController(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    //TODO error handling without ? wildcard
    @GetMapping("/suninfo")
    public ResponseEntity<SunriseSunsetDTO> getSunReport(@RequestParam(required = false) String date, @RequestParam(defaultValue = "Budapest") String city) {
        System.out.println(date);
        return ResponseEntity.ok(new SunriseSunsetDTO(openWeatherService.getSunriseSunsetInfo(city, date)));

    }

    @GetMapping("/current")
    public ResponseEntity<CurrentWeatherInfoDTO> getCurrentWeatherInfoFrom(@RequestParam(defaultValue = "Budapest") String city) {
        CurrentWeatherResponse currentWeatherDetailsResponse =
                this.openWeatherService.getCurrentWeatherResponseFor(city);
        return ResponseEntity.ok(this.openWeatherService.getCurrentWeatherInfoDTOFrom(currentWeatherDetailsResponse, city));
    }

}
