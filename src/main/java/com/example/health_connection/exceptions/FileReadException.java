package com.example.health_connection.exceptions;

public class FileReadException extends RuntimeException {
    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileReadException(String message) {
        super(message);
    }
}