package com.example.health_connection.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.Map;

public record ErrorResponseDto(
        int statusCode,
        String message,
        Map<String, Set<String>> errors,
        Instant timestamp
) {
    public ErrorResponseDto(int statusCode, String message, Map<String, Set<String>> errors) {
        this(statusCode, message, errors, Instant.now());
    }

    public ErrorResponseDto(int statusCode, String message) {
        this(statusCode, message, null);
    }
}