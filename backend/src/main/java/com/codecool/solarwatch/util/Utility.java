package com.codecool.solarwatch.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static LocalDate parseToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public static LocalTime converToLocalTime(String time) {
        int WRONG_TIME_FORMAT_LENGTH = 10;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        if (time.length() == WRONG_TIME_FORMAT_LENGTH) {
            String correctTimeFormat = "0" + time;
            return LocalTime.parse(correctTimeFormat, formatter);
        }
        return LocalTime.parse(time, formatter);
    }

    public static int convertToCelsius(double kelvin) {
        return (int) Math.round(kelvin - 273.15);
    }

    public static LocalDateTime convertUnixUTCToLocalDateTime(String unixUTC) {
        long unix_seconds = Long.parseLong(unixUTC);
        Instant instant = Instant.ofEpochMilli(unix_seconds);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        System.out.println(unixUTC);
        System.out.println(localDateTime);
        return localDateTime;
    }
}
