package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weatherforecast/admin/")
public class CityController {
    private final OpenWeatherService openWeatherService;

    @Autowired
    public CityController(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteCity(@RequestParam String cityName) {
        this.openWeatherService.deleteCityByName(cityName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateCity(@RequestParam String cityName) {
        this.openWeatherService.updateCityInfo(cityName);
        return ResponseEntity.ok().build();
    }
}
