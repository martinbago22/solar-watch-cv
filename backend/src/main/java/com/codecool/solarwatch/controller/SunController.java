package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.api.weather.current_weather_response.model.CurrentWeatherResponse;
import com.codecool.solarwatch.model.dto.CurrentWeatherInfoDTO;
import com.codecool.solarwatch.model.dto.SunriseSunsetDTO;
import com.codecool.solarwatch.service.MyWeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/weatherforecast")
public class SunController {
    private final MyWeatherAPIService myWeatherAPIService;

    @Autowired
    public SunController(MyWeatherAPIService myWeatherAPIService) {
        this.myWeatherAPIService = myWeatherAPIService;
    }

    @GetMapping("/suninfo")
    public ResponseEntity<SunriseSunsetDTO> getSunReport(@RequestParam String date, @RequestParam(defaultValue = "Budapest") String city) {
        //TODO Logging
        SunriseSunsetDTO sunriseSunsetDTO = new SunriseSunsetDTO(myWeatherAPIService.getSunriseSunsetInfo(city, date));
        return ResponseEntity.ok(sunriseSunsetDTO);
    }

    @GetMapping("/current")
    public ResponseEntity<CurrentWeatherInfoDTO> getCurrentWeatherInfoFrom(@RequestParam(defaultValue = "Budapest") String city) {
        CurrentWeatherResponse currentWeatherDetailsResponse =
                myWeatherAPIService.getCurrentWeatherResponseFor(city);
        CurrentWeatherInfoDTO currentWeatherInfoDTO = myWeatherAPIService.getCurrentWeatherInfoDTOFrom(currentWeatherDetailsResponse, city);
        return ResponseEntity.ok(currentWeatherInfoDTO);
    }

}
