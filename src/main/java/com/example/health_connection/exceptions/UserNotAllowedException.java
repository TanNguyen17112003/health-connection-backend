package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAllowedException extends CustomException {
    public UserNotAllowedException() {
        super("You are not allowed to do this", HttpStatus.FORBIDDEN);
    }
}
