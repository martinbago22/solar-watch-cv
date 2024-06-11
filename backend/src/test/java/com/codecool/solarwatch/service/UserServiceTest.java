package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static com.codecool.solarwatch.model.entity.Role.ROLE_ADMIN;
import static com.codecool.solarwatch.model.entity.Role.ROLE_USER;

class UserServiceTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    RoleRepository repository = Mockito.mock(RoleRepository.class);
    AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);
    UserService userService = new UserService(userRepository,
            repository, authenticationManager, passwordEncoder, jwtUtils);

    @Test
    void addRoleFor() {
        UserEntity user = new UserEntity("John", "doe");
        RoleEntity roleToBeAdded = new RoleEntity(ROLE_ADMIN);

        Mockito.when(user.getRoles()).thenReturn(Set.of(new RoleEntity(ROLE_USER)));

    }

    @Test
    void createUser() {
    }

    @Test
    void login() {
    }

    @Test
    void grantAdminPrivilegesFor() {
    }
}