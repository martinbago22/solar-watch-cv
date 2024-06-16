package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Entity
@Builder
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "city_id")
    private long id;
    @Setter
    @Getter
    @Column(name = "city_name")
    private String name;
    @Setter
    @Getter
    private double longitude;
    @Setter
    @Getter
    private double latitude;
    @Setter
    @Getter
    private String state;
    @Setter
    @Getter
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
