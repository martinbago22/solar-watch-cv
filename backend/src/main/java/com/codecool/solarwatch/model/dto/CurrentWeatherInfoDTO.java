package com.codecool.solarwatch.model.dto;

import java.text.SimpleDateFormat;

public record CurrentWeatherInfoDTO(String cityName, double temperature, double humidity, double windSpeed,
                                    String description, double visibility, String pictureTakingWarning,
                                    String sunriseTime, String sunsetTime) {
}
