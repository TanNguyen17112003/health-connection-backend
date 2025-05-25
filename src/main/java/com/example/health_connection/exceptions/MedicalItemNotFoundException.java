package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicalItemNotFoundException extends RuntimeException {
    public MedicalItemNotFoundException(String message) {
        super(message);
    }
}