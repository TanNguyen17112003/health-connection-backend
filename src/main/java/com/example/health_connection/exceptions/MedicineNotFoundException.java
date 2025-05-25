package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class MedicineNotFoundException extends CustomException {
    public MedicineNotFoundException() {
        super("Can not find any medicine with the provided info!", HttpStatus.NOT_FOUND);
    }
}
