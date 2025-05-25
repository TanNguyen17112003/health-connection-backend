package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super("Auth token is invalid", HttpStatus.UNAUTHORIZED);
    }
}