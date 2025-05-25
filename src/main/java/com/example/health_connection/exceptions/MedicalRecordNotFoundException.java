package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;

public class MedicalRecordNotFoundException extends CustomException {
    public MedicalRecordNotFoundException() {
        super("Can not find any medical records with the provided info!", HttpStatus.NOT_FOUND);
    }
}