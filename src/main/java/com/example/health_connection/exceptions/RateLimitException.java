package com.example.health_connection.exceptions;

import com.cloudinary.api.exceptions.RateLimited;

public class RateLimitException extends RateLimited {
    public RateLimitException(String message) {
        super(message);
    }
   
}