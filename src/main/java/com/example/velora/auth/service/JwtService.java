package com.example.velora.auth.service;

import com.example.velora.auth.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {

    private final String SECRET = "f9c2c6f6e1b4d9a7b1c2aef8e5d97c3f7c1b2d9a4f6e8c2b1a3d5e7f9c0b2a1d";

    private static final Duration ACCESS_VALIDITY = Duration.ofMinutes(15);

    private static final Duration REFRESH_VALIDITY = Duration.ofDays(7);

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private String buildToken(UserDetails user,
                              Duration validity,
                              TokenType type) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("type", type.name())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(validity)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateAccessToken(UserDetails user) {
        return buildToken(user, ACCESS_VALIDITY, TokenType.ACCESS);
    }

    public String generateRefreshToken(UserDetails user) {
        return buildToken(user, REFRESH_VALIDITY, TokenType.REFRESH);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateType(String token, TokenType expected) {
        String type = extractClaims(token).get("type", String.class);
        if(!expected.name().equals(type)) {
            throw new RuntimeException("Wrong token type");
        }
    }
}
