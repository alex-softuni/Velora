package com.example.velora.auth.service;

import com.example.velora.auth.entity.RefreshToken;
import com.example.velora.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void save(String token, UserDetails user) {

        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUsername(user.getUsername());
        rt.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));

        refreshTokenRepository.save(rt);
    }

    public RefreshToken verify(String token) {
        RefreshToken rt = refreshTokenRepository.findByToken(token).orElseThrow();

        if (rt.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        return rt;
    }
}
