package com.example.health_connection.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExerciseNotAllowedException extends RuntimeException {
    public ExerciseNotAllowedException(String message) {
        super(message);
    }
}