package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
<<<<<<< HEAD
=======

    void deleteByName(String name);
>>>>>>> 8064708 (please work)
}
