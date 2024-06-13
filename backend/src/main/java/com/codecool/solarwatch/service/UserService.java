package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidUserNameException;
import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.model.dto.UsernamePasswordDTO;
import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder encoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    //TODO error handling if user already has existing role to be added.
    public boolean addRoleFor(UserEntity user, Role role) {
        LOGGER.error("TEST");
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
    public boolean createUser(UsernamePasswordDTO usernamePasswordDTORequest) {
        if (!isValidRegisterRequest(usernamePasswordDTORequest)) {
            throw new InvalidUserNameException();
        }
        return attemptToCreateUser(usernamePasswordDTORequest);
    }

    //TODO mit küldünk itt vissza? String jwtToken? boolean? hol adjuk hozzá a role-t hogy a loginelt user már user roleban van?
    @Transactional
    public String login(UsernamePasswordDTO usernamePasswordDTORequest) {
        String jwt = null;
        try {
            //TODO kérdezni  ez a rész mit csinál?
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken
                            (usernamePasswordDTORequest.username(), usernamePasswordDTORequest.password()));
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
                .orElseThrow(()
                        -> new UsernameNotFoundException(String.format("Couldn't find user named [ %s ]", userName)));
    }

    private boolean userAlreadyExists(String userName) {
        Optional<UserEntity> searchedUser = this.userRepository.findUserEntityByUsername(userName);
        return searchedUser.isPresent();
    }

    @Transactional
    public void grantAdminPrivilegesFor(String userName) {
        UserEntity user = getUserBy(userName);
        addRoleFor(user, ROLE_ADMIN);
        LOGGER.info(String.format("Admin privileges granted for user %s%n", user.getUsername()));
    }

    private RoleEntity getRoleEntityBy(Role role) {
        return this.roleRepository.getRoleEntityByRole(role)
                .orElseThrow(()
                        -> new RuntimeException("Role not found"));
        // TODO proper error handling instead of RuntimeException
    }

    private boolean isValidRegisterRequest(UsernamePasswordDTO usernamePasswordDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UsernamePasswordDTO>> violations = validator.validate(usernamePasswordDTO);
        if (!violations.isEmpty()) {
            logViolationMessages(violations);
            return false;
        } else return true;
    }

    private void logViolationMessages(Set<ConstraintViolation<UsernamePasswordDTO>> violations) {
        violations
                .forEach(violation -> LOGGER.error(violation.getMessage()));
    }

    private void handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        if (e.getCause() instanceof SQLException sqlEx) {
            LOGGER.error("SQL error code: {}, state: {}, message: {}",
                    sqlEx.getErrorCode(), sqlEx.getSQLState(), sqlEx.getMessage());
        } else {
            LOGGER.error("Data access error: {}", e.getMessage());
        }
    }

    private boolean attemptToCreateUser(UsernamePasswordDTO usernamePasswordDTORequest) {
        if (!userAlreadyExists(usernamePasswordDTORequest.username())) {
            try {
                String hashedPassword = encoder.encode(usernamePasswordDTORequest.password());
                UserEntity newUser = new UserEntity(usernamePasswordDTORequest.username(), hashedPassword);

                if (addRoleFor(newUser, ROLE_USER)) {
                    userRepository.save(newUser);
                    LOGGER.info("USER: [{}] saved to database", newUser.getUsername());
                    return true;
                } else {
                    return false;
                }
            } catch (DataIntegrityViolationException e) {
                handleDataIntegrityViolationException(e);
                return false;
            } catch (UnexpectedRollbackException e) {
                LOGGER.error("Transaction rollback occurred: {}", e.getMessage());
                return false;
            } catch (RuntimeException e) {
                LOGGER.error("An unexpected error occurred: {}", e.getMessage());
                return false;
            }
        } else {
            LOGGER.error("Username {} is already in use", usernamePasswordDTORequest.username());
            throw new UserAlreadyExistsException(usernamePasswordDTORequest.username());
        }
    }
}
