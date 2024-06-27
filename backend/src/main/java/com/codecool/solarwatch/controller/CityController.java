package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weatherforecast/admin/")
public class CityController {
    private final SolarWatchService solarWatchService;

    @Autowired
    public CityController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteCity(@RequestParam String cityName) {
        this.solarWatchService.deleteCityByName(cityName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateCity(@RequestParam String cityName) {
        this.solarWatchService.updateCityInfo(cityName);
        return ResponseEntity.ok().build();
    }
}
