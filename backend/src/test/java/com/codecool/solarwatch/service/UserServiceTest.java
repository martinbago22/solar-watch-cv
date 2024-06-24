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
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final UserEntity user = new UserEntity("John", "doe");

    @BeforeEach
    void setup() {
        userRepository.save(user);
    }


    @Nested
    @DisplayName("Test cases for valid attempts at adding admin role to user")
    class WhenAddRoleForIsSuccessful {
        @Test
        @DisplayName("grantAdminTo successfully adds admin role to user when provided valid parameters")
        void WhenProvidedValidUserName_ThenGrantAdminToAddsAdminRoleToUser() {
            RoleEntity expected = new RoleEntity(ROLE_ADMIN);

            when(userRepository.findUserEntityByUsername(anyString()))
                    .thenReturn(Optional.of(user));
            userService.grantAdminTo(user.getUsername());

            verify(userRepository, times(1)
                    .description("GetRoleEntityByRole gets called one time"))
                    .findUserEntityByUsername(user.getUsername());
            boolean containsRequestedRole = user.getRoles().contains(expected);

            assertTrue(containsRequestedRole);
        }

        @Test
        @DisplayName("grantAdminTo returns true after successfully adding admin role to user")
        void WhenProvidedValidUserName_ThenGrantAdminToReturnsTrue() {
            when(userRepository.findUserEntityByUsername(anyString()))
                    .thenReturn(Optional.of(user));
            boolean result = userService.grantAdminTo(user.getUsername());

            verify(userRepository, times(1))
                    .findUserEntityByUsername(user.getUsername());

            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("Test cases for invalid attempts at adding admin role to user")
    class WhenAddRoleForIsUnsuccessful {
        @Test
        @DisplayName("grantAdminTo throws username not found exception when user is not found")
        void WhenProvidedInvalidUserAsParameter_ThenGrantAdminToThrowsRuntimeException_() {
            when(userRepository.findUserEntityByUsername(anyString()))
                    .thenReturn(Optional.empty());
            RuntimeException expected = assertThrows(UsernameNotFoundException.class,
                    () -> userService.grantAdminTo(user.getUsername()));

            verify(userRepository, times(1))
                    .findUserEntityByUsername(user.getUsername());
            assertEquals(expected.getMessage(), String.format("Couldn't find user named [ %s ]", user.getUsername()));
            assertThrows(RuntimeException.class, () -> userService.grantAdminTo(user.getUsername()));
        }

        @Test
        @DisplayName("grantAdminTo returns false when user already has admin role")
        void WhenUserAlreadyHasAdminRole_ThenGrantAdminToReturnsFalse() {
            RoleEntity alreadyExistingRole = new RoleEntity(ROLE_ADMIN);
            user.setRoles(Set.of(alreadyExistingRole));

            when(userRepository.findUserEntityByUsername(anyString()))
                    .thenReturn(Optional.of(user));
            boolean result = userService.grantAdminTo(user.getUsername());

            verify(userRepository, times(1))
                    .findUserEntityByUsername(user.getUsername());
            assertFalse(result);
        }

        @Test
        @DisplayName("grantAdminTo throws username not found exception when provided null as username")
        void WhenNullIsGivenAsUserName_ThenUserNameNotFoundExceptionIsThrown() {
            UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class,
                    () -> userService.grantAdminTo(null));

            verify(userRepository, times(1))
                    .findUserEntityByUsername(null);

            assertEquals(e.getMessage(), "Couldn't find user named [ null ]");
            assertThrows(UsernameNotFoundException.class, () -> userService.grantAdminTo(null));
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
    void CreateUser_ThrowsUsernameIsAlreadyExistsException_WhenProvidedUserNameAlreadyExists() {
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

        //assertTrue(userService.grantAdminPrivilegesFor(username));
    }

    private Set<ConstraintViolation<RegisterRequestDTO>> validate(RegisterRequestDTO underTest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(underTest);
    }

    private String getViolationMessage(Set<ConstraintViolation<RegisterRequestDTO>> violations) {
        return violations.iterator().next().getMessage();
    }

}