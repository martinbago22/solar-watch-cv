package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sunrise_sunset_info")
public class SunriseSunsetInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sunrise_sunset_id")
    private long id;
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    @Temporal(TemporalType.TIME)
    private LocalTime sunriseTime;

    @Temporal(TemporalType.TIME)
    private LocalTime sunsetTime;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public SunriseSunsetInfo(LocalDate date, LocalTime sunriseTime, LocalTime sunsetTime, City city) {
        this.date = date;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.city = city;
    }

    public SunriseSunsetInfo() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(LocalTime sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public LocalTime getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(LocalTime sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
