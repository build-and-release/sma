package com.technovate.school_management.controller;

import com.technovate.school_management.dto.CreateUserDto;
import com.technovate.school_management.dto.LoginDto;
import com.technovate.school_management.mapper.UserMapper;
import com.technovate.school_management.model.ApiResponseType;
import com.technovate.school_management.model.AuthenticationResponse;
import com.technovate.school_management.service.contracts.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseType<AuthenticationResponse>> register(@RequestBody CreateUserDto createUserDto) {
        AuthenticationResponse response = authService.addUser(createUserDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(response, "Registration successful"));
    }

    @PostMapping("/login")
   public ResponseEntity<ApiResponseType<AuthenticationResponse>> login(@RequestBody LoginDto loginDto) {
        AuthenticationResponse response = authService.loginUser(loginDto);
        return ResponseEntity.ok(ApiResponseType.generateSuccessResponse(response, "Login successful"));
    }
}
