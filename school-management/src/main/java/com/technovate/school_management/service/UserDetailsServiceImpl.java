package com.technovate.school_management.service;

import com.technovate.school_management.entity.User;
import com.technovate.school_management.repository.UserRepository;
import com.technovate.school_management.service.contracts.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements AuthService.UserDetailsService {
    final private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}