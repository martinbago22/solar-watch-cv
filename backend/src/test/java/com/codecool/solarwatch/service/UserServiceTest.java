package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.dto.UsernamePasswordDTO;
import com.codecool.solarwatch.model.entity.Role;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void AddRoleForUser_SuccessfullyAddsRequestedRoleToUser_WhenProvidedValidParameters() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        when(roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));
        userService.addRoleFor(user, ROLE_ADMIN);
        boolean containsRequestedRole = user.getRoles().contains(new RoleEntity(ROLE_ADMIN));

        assertTrue(containsRequestedRole);
    }

    @Test
    void AddRoleForUser_ThrowsRuntimeException_WhenProvidedInvalidRoleAsParameter() {
        UserEntity user = new UserEntity("John", "doe");
        Role mockRole = mock();

        when(roleRepository.getRoleEntityByRole(mockRole)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.addRoleFor(user, mockRole));
    }

    @Test
    void AddRoleForUser_ReturnsTrue_WhenProvidedValidParameters() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        when(roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

        assertTrue(userService.addRoleFor(user, ROLE_ADMIN));
    }

    @Test
    void AddRoleFor_ReturnsFalse_WhenUserAlreadyHasProvidedRole() {
        UserEntity user = new UserEntity("John", "doe");
        user.setRoles(Set.of(new RoleEntity(ROLE_ADMIN)));

        when(this.roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

        assertFalse(this.userService.addRoleFor(user, ROLE_ADMIN));
    }

    @Test
    void AddRoleFor_ThrowsRuntimeException_WhenProvidedNullAsRole() {
        UserEntity user = new UserEntity("John", "doe");

        assertThrows(RuntimeException.class, () -> this.userService.addRoleFor(user, null));
    }

    @Test
    void CreateUser_ReturnsTrue_WhenProvidedValidParameters() {
        UsernamePasswordDTO usernamePasswordDTO = new UsernamePasswordDTO("John", "doe");
        UserEntity expectedEncodedUser = new UserEntity("John", "encoded");

        when(passwordEncoder.encode(usernamePasswordDTO.password()))
                .thenReturn("encoded");
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(expectedEncodedUser);


        boolean created = userService.createUser(usernamePasswordDTO);

        assertTrue(created);
    }

    @Test
    void createUser_ThrowsUsernameIsAlreadyExistsException_WhenProvidedUserNameAlreadyExists() {
        UsernamePasswordDTO usernamePasswordDTO = new UsernamePasswordDTO("John", "doe");
        UserEntity alreadyExistingUser = new UserEntity("John", "abc");

        when(this.userRepository.findUserEntityByUsername(usernamePasswordDTO.username()))
                .thenReturn(Optional.of(alreadyExistingUser));

        assertThrows(UserAlreadyExistsException.class, () -> this.userService.createUser(usernamePasswordDTO));
    }

    @Test
    void login() {
    }

    @Test
    void GrantAdminPrivilegesForGrantsAdminRoleForWhenProvidedValidUsername() {
        String username = "john";
        UserEntity user = new UserEntity(username, "doe");
    }

}