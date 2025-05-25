package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super("Can not find any user with the provided info!", HttpStatus.NOT_FOUND);
    }
}