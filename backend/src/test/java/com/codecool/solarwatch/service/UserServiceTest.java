package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.dto.UsernamePasswordDTO;
import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static com.codecool.solarwatch.model.entity.Role.ROLE_ADMIN;
import static com.codecool.solarwatch.model.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private Logger logger;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private UserService userService;


    @Test
    void AddRoleForUserSuccessfullyAddsRequestedRoleToUserWhenProvidedValidParameters() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        when(this.roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));
        this.userService.addRoleFor(user, ROLE_ADMIN);
        boolean containsRequestedRole = user.getRoles().contains(new RoleEntity(ROLE_ADMIN));

        assertTrue(containsRequestedRole);
    }

    @Test
    void AddRoleForUserReturnsTrueWhenProvidedValidParameters() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        when(this.roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

        assertTrue(this.userService.addRoleFor(user, ROLE_ADMIN));
    }

    @Test
    void addRoleForReturnsFalseWhenUserAlreadyHasProvidedRole() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_ADMIN)));

        when(this.roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

        assertFalse(this.userService.addRoleFor(user, ROLE_ADMIN));
    }

    @Test
    void addRoleForThrowsRuntimeExceptionWhenProvidedNullAsRole() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        when(this.roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

        assertThrows(RuntimeException.class, () -> this.userService.addRoleFor(user, null));
    }

    @Test
    // TODO get help.
    void createUserReturnsTrueWhenProvidedValidParameters() {
        UsernamePasswordDTO usernamePasswordDTO = new UsernamePasswordDTO("John", "doe");
        UserEntity user = new UserEntity(usernamePasswordDTO);
        UserEntity expectedEncodedUser = new UserEntity("John", "encoded");
        RoleEntity expectedRole = new RoleEntity(ROLE_USER);
        expectedEncodedUser.setRoles(Set.of(expectedRole));

        when(this.passwordEncoder.encode(usernamePasswordDTO.password()))
                .thenReturn("encoded");
        when(this.roleRepository.getRoleEntityByRole(ROLE_USER))
                .thenReturn(Optional.of(new RoleEntity(ROLE_USER)));
        when(this.userRepository.save(user))
                .thenReturn(expectedEncodedUser);

        boolean created = this.userService.createUser(usernamePasswordDTO);

        assertTrue(created);
    }

    @Test
    void login() {
    }

    @Test
    void grantAdminPrivilegesForGrantsAdminRoleForWhenProvidedValidUsername() {
        String username = "john";
        UserEntity user = new UserEntity(username, "doe");

    }
}