package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends CustomException {
    public PasswordNotMatchException() {
        super("The current password is not correct", HttpStatus.FORBIDDEN);
    }
}
