package com.technovate.school_management.service;

import com.technovate.school_management.service.contracts.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${application_config.jwt_config.key}")
    private String SECRET_KEY;

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return (Claims) Jwts.parser().verifyWith(getSigningKey()).build().parse(token).getPayload();
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date currentDate = new Date(System.currentTimeMillis());
        var tokenExpiration = extractClaim(token, Claims::getExpiration);
        System.out.println("Current Date: " + currentDate + " \n" + "tokenExpiration: " + tokenExpiration);
        return currentDate.after(tokenExpiration);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
