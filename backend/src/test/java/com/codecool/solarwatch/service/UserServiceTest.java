package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.dto.RegisterRequestDTO;
import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    private static UserEntity user;

    @BeforeAll
    static void setup() {
        user = new UserEntity("John", "doe");
    }


    @Nested
    @DisplayName("Test cases for valid attempts at adding specified role to user")
    class WhenAddRoleForIsSuccessful {
        @Test
        void AddRoleForUser_SuccessfullyAddsRequestedRoleToUser_WhenProvidedValidParameters() {
            user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

            when(roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));
            userService.addRoleFor(user, ROLE_ADMIN);
            boolean containsRequestedRole = user.getRoles().contains(new RoleEntity(ROLE_ADMIN));

            assertTrue(containsRequestedRole);
        }

        @Test
        void AddRoleForUser_ReturnsTrue_WhenProvidedValidParameters() {
            user.setRoles(Set.of(new RoleEntity(ROLE_USER)));

            when(roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

            assertTrue(userService.addRoleFor(user, ROLE_ADMIN));
        }
    }

    @Nested
    @DisplayName("Test cases for invalid attempts at adding specified role to user")
    class WhenAddRoleForIsUnsuccessful {
        @Test
        void AddRoleForUser_ThrowsRuntimeException_WhenProvidedInvalidRoleAsParameter() {
            Role mockRole = mock();

            when(roleRepository.getRoleEntityByRole(mockRole)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> userService.addRoleFor(user, mockRole));
        }

        @Test
        void AddRoleFor_ReturnsFalse_WhenUserAlreadyHasProvidedRole() {
            user.setRoles(Set.of(new RoleEntity(ROLE_ADMIN)));

            when(roleRepository.getRoleEntityByRole(ROLE_ADMIN)).thenReturn(Optional.of(new RoleEntity(ROLE_ADMIN)));

            assertFalse(userService.addRoleFor(user, ROLE_ADMIN));
        }

        @Test
        void AddRoleFor_ThrowsRuntimeException_WhenProvidedNullAsRole() {
            Set<ConstraintViolation<String>> set = null;
        }
    }

    @Test
    void CreateUser_ReturnsTrue_WhenProvidedValidParameters() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("qwe", "doe");
        UserEntity expectedEncodedUser = new UserEntity(registerRequestDTO.username(), "asd");
        expectedEncodedUser.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        userService.createUser(registerRequestDTO);
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        UserEntity actual = argumentCaptor.getValue();

        System.out.println(expectedEncodedUser);
        System.out.println(actual);

        assertEquals(expectedEncodedUser, actual);
    }

    @Test
    void CreateUser_ThrowsIllegalArgumentException_WhenProvidedNullAsParameter() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
    }

    @Test
    void createUser_ThrowsUsernameIsAlreadyExistsException_WhenProvidedUserNameAlreadyExists() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("John", "doe");

        when(this.userRepository.existsByUsername(registerRequestDTO.username()))
                .thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> this.userService.createUser(registerRequestDTO));
        verify(userRepository, never()).save(new UserEntity(registerRequestDTO));
    }

    @Test
    void login() {
    }

    @Test
    void GrantAdminPrivilegesFor_GrantsAdminRoleForUser_WhenProvidedValidUsername() {
        String username = "john";
        UserEntity user = new UserEntity(username, "doe");
        userRepository.save(user);
        UserEntity expectedUserAfterSave = mock();
        expectedUserAfterSave.setRoles(Set.of(new RoleEntity(ROLE_ADMIN)));

        when(userRepository.findUserEntityByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        when(roleRepository.getRoleEntityByRole(any(Role.class)))
                .thenReturn(Optional.of(mock(RoleEntity.class)));

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(expectedUserAfterSave);

        assertTrue(userService.grantAdminPrivilegesFor(username));
    }

    private Set<ConstraintViolation<RegisterRequestDTO>> validate(RegisterRequestDTO underTest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(underTest);
    }

    private String getViolationMessage(Set<ConstraintViolation<RegisterRequestDTO>> violations) {
        return violations.iterator().next().getMessage();
    }

}