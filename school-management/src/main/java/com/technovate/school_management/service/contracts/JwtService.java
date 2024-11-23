package com.technovate.school_management.service.contracts;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String token);
    Claims extractAllClaims(String token);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
