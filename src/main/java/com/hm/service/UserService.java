package com.hm.service;
 
import com.hm.entity.User;

import com.hm.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
 
import java.util.Optional;
 
@Service

public class UserService implements UserDetailsService {
 
    private final UserRepository userRepository;
 
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }
 
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
 
        if (user.isEmpty()) {

            throw new UsernameNotFoundException("User not found: " + username);

        }
 
        return org.springframework.security.core.userdetails.User.builder()

                .username(user.get().getUsername())

                .password(user.get().getPassword())

                .roles(user.get().getRole()) // Assuming the role is stored as "USER", "ADMIN", etc.

                .build();

    }

}

 