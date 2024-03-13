package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Role> {
    Optional<RoleEntity> getRoleEntityByRole(Role role);
}
