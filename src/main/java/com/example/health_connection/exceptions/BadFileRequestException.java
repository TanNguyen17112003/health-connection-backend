package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class BadFileRequestException extends CustomException {
    public BadFileRequestException() {
        super("The file upload is not suitable", HttpStatus.NOT_FOUND);
    }
}