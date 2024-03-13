package com.codecool.solarwatch.configuration;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseInitializer {
    private final RoleRepository roleRepository;

    @Autowired
    public DataBaseInitializer(RoleRepository repository) {
        this.roleRepository = repository;
    }
    @Bean
    public CommandLineRunner initializeRoles() {
        return args -> {
            for (Role role : Role.values()) {
                this.roleRepository
                        .save(new RoleEntity(role));
            }
        };
    }
}
