package com.technovate.school_management.service.contracts;

import com.technovate.school_management.dto.CreateUserDto;
import com.technovate.school_management.dto.LoginDto;
import com.technovate.school_management.model.AuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    AuthenticationResponse addUser(CreateUserDto createUserDto);
    AuthenticationResponse loginUser(LoginDto loginDto);

    interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
        UserDetails loadUserByUsername(String username);
    }
}
