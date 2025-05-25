package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException() {
        super("Email or password is incorrect!", HttpStatus.UNAUTHORIZED);
    }
}