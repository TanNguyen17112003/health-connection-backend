package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class PrescriptionNotFoundException extends CustomException {
    public PrescriptionNotFoundException() {
        super("Can not find any medicine with the provided info!", HttpStatus.NOT_FOUND);
    }
}
