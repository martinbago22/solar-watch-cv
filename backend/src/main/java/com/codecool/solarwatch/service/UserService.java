package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.dto.RegisterRequestDTO;
import com.codecool.solarwatch.model.entity.Role;
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
    //TODO public methods on top

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public boolean createUser(@NonNull RegisterRequestDTO registerRequestDTORequest) {
        return attemptToCreateUser(registerRequestDTORequest);
    }

    @Transactional
    public boolean addRoleFor(UserEntity user, Role role) {
        Set<RoleEntity> oldRoles = user.getRoles();
        RoleEntity roleToBeAdded = getRoleEntityBy(role);
        if (!oldRoles.contains(roleToBeAdded)) {
            Set<RoleEntity> copiedRoles = new HashSet<>(oldRoles);
            copiedRoles.add(roleToBeAdded);
            user.setRoles(copiedRoles);
            userRepository.save(user);
            LOGGER.info(String.format("ROLE: [%s] has been added to USER: [%s]", role, user));
            return true;
        } else {
            LOGGER.error(String.format("USER: [%s] already has existing ROLE: [%s]", user, role));
            return false;
        }
    }

    @Transactional
    public boolean grantAdminPrivilegesFor(String userName) {
        UserEntity user = getUserBy(userName);
        LOGGER.info(String.format("Admin privileges granted for user %s%n", user.getUsername()));
        return addRoleFor(user, ROLE_ADMIN);
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
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Couldn't find user named [ %s ]", userName)));
    }

    private RoleEntity getRoleEntityBy(Role role) {
        return this.roleRepository.getRoleEntityByRole(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    private void userAlreadyExists(String userName) {
        boolean userExists = userRepository.existsByUsername(userName);
        if (userExists) {
            throw new UserAlreadyExistsException(userName);
        }
    }

    private boolean attemptToCreateUser(RegisterRequestDTO registerRequestDTORequest) {
        userAlreadyExists(registerRequestDTORequest.username());

        String hashedPassword = encoder.encode(registerRequestDTORequest.password());
        UserEntity newUser = new UserEntity(registerRequestDTORequest.username(), hashedPassword);
        newUser.setRoles(Set.of(new RoleEntity(ROLE_USER)));

        userRepository.save(newUser);
        LOGGER.info("USER: [{}] saved to database", newUser.getUsername());
        return true;
    }

}
