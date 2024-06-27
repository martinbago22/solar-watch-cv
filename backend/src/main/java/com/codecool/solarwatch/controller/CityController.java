package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.service.MyWeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weatherforecast/admin/")
public class CityController {
    private final MyWeatherAPIService myWeatherAPIService;

    @Autowired
    public CityController(MyWeatherAPIService myWeatherAPIService) {
        this.myWeatherAPIService = myWeatherAPIService;
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteCity(@RequestParam String cityName) {
        this.myWeatherAPIService.deleteCityByName(cityName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateCity(@RequestParam String cityName) {
        this.myWeatherAPIService.updateCityInfo(cityName);
        return ResponseEntity.ok().build();
    }
}
