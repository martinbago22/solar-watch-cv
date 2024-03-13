package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.dto.UsernamePasswordDTO;
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

    public void addRoleFor(UserEntity user, Role role) {
        Set<RoleEntity> oldRoles = user.getRoles();
        RoleEntity roleEntity = getRoleEntityBy(role);

        Set<RoleEntity> copiedRoles = new HashSet<>(oldRoles);
        copiedRoles.add(roleEntity);
        user.setRoles(copiedRoles);
        LOGGER.info(String.format("Successfully added role to user %s%n", user));

        userRepository.save(user);
        LOGGER.info("Updated user successfully saved to DB");
    }

    @Transactional
    public boolean createUser(UsernamePasswordDTO usernamePasswordDTORequest) {
        try {
            String hashedPassword = encoder.encode(usernamePasswordDTORequest.password());
            UserEntity newUser = new UserEntity(usernamePasswordDTORequest.username(), hashedPassword);
            addRoleFor(newUser, Role.ROLE_USER);
            this.userRepository.save(newUser);
            return true;
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
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
        return this.userRepository.findUserEntitiesByUsername(userName)
                .orElseThrow(()
                        -> new UsernameNotFoundException("Couldn't find user by that name"));
    }

    @Transactional
    public void grantAdminPrivilegesFor(String userName) {
        UserEntity user = getUserBy(userName);
        addRoleFor(user, Role.ROLE_ADMIN);
        LOGGER.info(String.format("Admin privileges granted for user %s%n", user.getUsername()));
    }

    private RoleEntity getRoleEntityBy(Role role) {
        return this.roleRepository.getRoleEntityByRole(role)
                .orElseThrow(()
                        -> new RuntimeException("Role not found"));
        // TODO proper error handling instead of RuntimeException
    }
}
