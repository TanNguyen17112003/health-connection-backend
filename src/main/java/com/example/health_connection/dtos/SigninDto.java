package com.example.health_connection.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SigninDto {
    @NotBlank(message="Email is required")
    @Email(message="Email is not valid")
    private String email;

    @NotBlank(message="Password is required")
    private String password;
}
