package com.codecool.solarwatch.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static Date convertUnixUTCToDate(String unixUTC) {
        long unix_seconds = Long.parseLong(unixUTC);
        Date date = new Date(unix_seconds * 1000L);
        System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
        return sdf.format(date);
    }
}
