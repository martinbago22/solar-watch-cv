package com.codecool.solarwatch.exception;

import com.codecool.solarwatch.model.entity.Role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Role role) {
        super(String.format("Role: [%s] not found", role.toString()));
    }
}
