package com.codecool.solarwatch.model.entity;

import com.codecool.solarwatch.api.geocoding.model.Coordinates;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "city_id")
    private long id;

    @Column(name = "city_name")
    private String name;

    private double longitude;

    private double latitude;

    private String state;

    private String country;
    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<SunriseSunsetInfo> sunriseSunsetInfoList;

    public City(String name, double longitude, double latitude, String state, String country) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.country = country;
    }

    public City(String cityName, Coordinates coordinates) {
        this.name = cityName;
        this.longitude = coordinates.longitude();
        this.latitude = coordinates.latitude();
        this.state = coordinates.state();
        this.country = coordinates.country();
    }

    public City() {

    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<SunriseSunsetInfo> getSunriseSunsetInfoList() {
        return sunriseSunsetInfoList;
    }

    public void setSunriseSunsetInfoList(List<SunriseSunsetInfo> sunriseSunsetInfoList) {
        this.sunriseSunsetInfoList = sunriseSunsetInfoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && Double.compare(longitude, city.longitude) == 0 && Double.compare(latitude, city.latitude) == 0 && Objects.equals(name, city.name) && Objects.equals(state, city.state) && Objects.equals(country, city.country) && Objects.equals(sunriseSunsetInfoList, city.sunriseSunsetInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, longitude, latitude, state, country, sunriseSunsetInfoList);
    }

}
