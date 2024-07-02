package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.dto.RegisterRequestDTO;
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
@DisplayName("UserService Unit test")
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
    private final RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(user.getUsername(), user.getPassword());

    @BeforeEach
    void setup() {
        lenient().when((userRepository.save(any(UserEntity.class)))).thenReturn(user);

        // Mock the findById or findByUsername method to return the user
        lenient().when(userRepository.findUserEntityByUsername(anyString())).thenReturn(Optional.of(user));
    }


    @Nested
    @DisplayName("Test cases for valid attempts at adding admin role to user")
    class WhenGrantAdminToIsSuccessful {
        @Test
        @DisplayName("grantAdminTo successfully adds admin role to user when provided valid parameters")
        void WhenProvidedValidUserName_ThenGrantAdminToAddsAdminRoleToUser() {
            RoleEntity expected = new RoleEntity(ROLE_ADMIN);
            ;
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
            boolean result = userService.grantAdminTo(user.getUsername());

            verify(userRepository, times(1))
                    .findUserEntityByUsername(user.getUsername());

            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("Test cases for invalid attempts at adding admin role to user")
    class WhenGrantAdminToIsUnsuccessful {
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

    @Nested
    @DisplayName("Test cases for successful user creations")
    class WhenCreateUserIsSuccessful {
        @Test
        @DisplayName("createUser returns true with valid register request dto")
        void WhenGivenValidRegisterRequest_ThenCreateUserReturnsTrue() {
            RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("John", "password123");

            when(passwordEncoder.encode(anyString())).thenReturn("encoded");
            when(roleRepository.getRoleEntityByRole(ROLE_USER)).thenReturn(Optional.of(new RoleEntity(ROLE_USER)));

            boolean result = userService.createUser(registerRequestDTO);

            ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
            verify(userRepository, times(1)).save(userCaptor.capture());

            UserEntity capturedUser = userCaptor.getValue();

            assertEquals("encoded", capturedUser.getPassword());
            assertEquals("John", capturedUser.getUsername());
            assertTrue(capturedUser.getRoles().stream().anyMatch(role -> role.getRole() == ROLE_USER));
            assertTrue(result);
        }

        @Test
        @DisplayName("createUser successfully adds role User to registered user")
        void WhenUserIsCreated_ThenUserRoleIsAdded() {
            RoleEntity expectedRole = new RoleEntity(ROLE_USER);

            when(passwordEncoder.encode(anyString()))
                    .thenReturn("encoded");
            when(roleRepository.getRoleEntityByRole(ROLE_USER))
                    .thenReturn(Optional.of(expectedRole));

            ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
            userService.createUser(registerRequestDTO);
            verify(userRepository).save(userCaptor.capture());
            UserEntity savedUser = userCaptor.getValue();
            boolean result = savedUser.getRoles().contains(expectedRole);


            assertTrue(result);
            assertFalse(savedUser.getRoles().isEmpty());
            assertEquals("John", savedUser.getUsername());
            assertEquals("encoded", savedUser.getPassword());
        }
    }

    @Nested
    @DisplayName("Test cases for unsuccessful attempts at creating user")
    class WhenCreateUserIsUnsuccessful {
        @Test
        @DisplayName("createUser throws illegal argument exception when given null as register request")
        void WhenProvidedNullAsParameter_ThenCreateUserThrowsIllegalArgumentException_() {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
            String expectedMessage = "registerRequestDTO cannot be null";
            String actualMessage = e.getMessage();

            verify(userRepository, never()).save(any(UserEntity.class));
            assertEquals(expectedMessage, actualMessage);
            assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
        }

        @Test
        @DisplayName("createUser throws username already exists exception when given already existing username")
        void WhenProvidedAlreadyExistingUserName_ThenCreateUserThrowsUserNameAlreadyExistsException() {

            RegisterRequestDTO sameNameRegisterAttempt = new RegisterRequestDTO("John", "doe");
            when(userRepository.existsByUsername(sameNameRegisterAttempt.username()))
                    .thenReturn(true);
            UserAlreadyExistsException e = assertThrows(UserAlreadyExistsException.class,
                    () -> userService.createUser(sameNameRegisterAttempt));
            String expectedErrorMessage = String.format("Username: [ %s ] is already in use", sameNameRegisterAttempt.username());
            String actualErrorMessage = e.getMessage();

            when(userRepository.existsByUsername(registerRequestDTO.username()))
                    .thenReturn(true);


            verify(userRepository, never()).save(any(UserEntity.class));
            assertEquals(expectedErrorMessage, actualErrorMessage);
            assertTrue(userRepository.existsByUsername(sameNameRegisterAttempt.username()));
        }
    }


    @Test
    void login() {
    }


    private Set<ConstraintViolation<RegisterRequestDTO>> validate(RegisterRequestDTO underTest) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(underTest);
    }

    private String getViolationMessage(Set<ConstraintViolation<RegisterRequestDTO>> violations) {
        return violations.iterator().next().getMessage();
    }

}