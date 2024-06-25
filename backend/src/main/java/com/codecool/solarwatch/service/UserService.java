package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.dto.RegisterRequestDTO;
import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codecool.solarwatch.model.entity.Role.ROLE_ADMIN;
import static com.codecool.solarwatch.model.entity.Role.ROLE_USER;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public boolean createUser(@NonNull RegisterRequestDTO registerRequestDTO) {
        if (registerRequestDTO == null) {
            LOGGER.error("registerRequestDTO passed in as null");
            throw new IllegalArgumentException("registerRequestDTO cannot be null");
        }
        return attemptToCreateUser(registerRequestDTO);
    }

    @Transactional
    public boolean grantAdminTo(@NonNull String userName) {
        UserEntity user = getUserBy(userName);
        Set<RoleEntity> oldRoles = user.getRoles();
        RoleEntity adminRole = new RoleEntity(ROLE_ADMIN);
        if (!oldRoles.contains(adminRole)) {
            Set<RoleEntity> copiedRoles = new HashSet<>(oldRoles);
            copiedRoles.add(adminRole);
            user.setRoles(copiedRoles);
            userRepository.save(user);
            LOGGER.info(String.format("ROLE: [Admin] has been added to USER: [%s]", userName));
            return true;
        } else {
            LOGGER.error(String.format("USER: [%s] already has Admin role", user));
            return false;
        }
    }

    //TODO mit küldünk itt vissza? String jwtToken? boolean? hol adjuk hozzá a role-t hogy a loginelt user már user roleban van?
    @Transactional
    public String login(RegisterRequestDTO registerRequestDTORequest) {
        String jwt = null;
        try {
            //TODO kérdezni  ez a rész mit csinál?
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequestDTORequest.username(), registerRequestDTORequest.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwt = jwtUtils.generateJwtToken(authentication);

            User userDetails = (User) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return jwt;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return jwt;
    }

    private UserEntity getUserBy(String userName) {
        return this.userRepository.findUserEntityByUsername(userName)
                .orElseThrow(() -> {
                    LOGGER.error(String.format("USER: [%s] was not found or doesn't exist", userName));
                    return new UsernameNotFoundException(String.format("Couldn't find user named [ %s ]", userName));
                });
    }

    private void userAlreadyExists(String userName) {
        boolean userExists = userRepository.existsByUsername(userName);
        if (userExists) {
            LOGGER.error("USER: [{}] already exists", userName);
            throw new UserAlreadyExistsException(userName);
        }
    }

    private boolean attemptToCreateUser(@NonNull RegisterRequestDTO registerRequestDTORequest) {
        userAlreadyExists(registerRequestDTORequest.username());

        String hashedPassword = encoder.encode(registerRequestDTORequest.password());
        UserEntity newUser = new UserEntity(registerRequestDTORequest.username(), hashedPassword);
        RoleEntity userRole = roleRepository.getRoleEntityByRole(ROLE_USER)
                .orElse(new RoleEntity(ROLE_USER));
        newUser.setRoles(Set.of(userRole));

        userRepository.save(newUser);
        LOGGER.info("USER: [{}] saved to database", newUser.getUsername());
        return true;
    }

}
