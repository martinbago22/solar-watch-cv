package com.codecool.solarwatch.security.service;

import com.codecool.solarwatch.model.entity.RoleEntity;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity roleEntity : userEntity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(roleEntity.getRole().name()));
            //for (var authority : role.getAuthorities())
        }

        //TODO authorityket rendelni a roleokhoz és userhez a saját rolejaihoz tartozó authorityket hozzárendelni

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
