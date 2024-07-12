package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.api.current_weather.model.CurrentWeatherResponse;
import com.codecool.solarwatch.model.dto.CurrentWeatherInfoDTO;
import com.codecool.solarwatch.model.dto.SunriseSunsetDTO;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/weatherforecast")
public class SunController {
    private final SolarWatchService solarWatchService;

    @Autowired
    public SunController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping("/suninfo")
    public ResponseEntity<SunriseSunsetDTO> getSunReport(@RequestParam String date, @RequestParam String city) {
        //TODO Logging
        SunriseSunsetDTO sunriseSunsetDTO = new SunriseSunsetDTO(solarWatchService.getSunriseSunsetInfo(city, date));
        return ResponseEntity.ok(sunriseSunsetDTO);
    }

    @GetMapping("/current")
    public ResponseEntity<CurrentWeatherInfoDTO> getCurrentWeatherInfoFrom(@RequestParam String city) {
        CurrentWeatherResponse currentWeatherDetailsResponse =
                solarWatchService.getCurrentWeatherResponseFor(city);
        CurrentWeatherInfoDTO currentWeatherInfoDTO = solarWatchService.getCurrentWeatherInfoDTOFrom(currentWeatherDetailsResponse, city);
        return ResponseEntity.ok(currentWeatherInfoDTO);
    }

}
