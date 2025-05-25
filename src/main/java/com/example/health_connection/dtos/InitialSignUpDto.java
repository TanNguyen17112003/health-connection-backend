package com.example.health_connection.dtos;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class InitialSignUpDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email;
}
