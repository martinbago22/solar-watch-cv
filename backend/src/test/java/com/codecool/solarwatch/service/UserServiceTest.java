package com.codecool.solarwatch.service;

import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

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