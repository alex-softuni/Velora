package com.example.velora.auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
