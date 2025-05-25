package com.example.health_connection.dtos;

import java.time.Instant;

import com.example.health_connection.enums.UserGender;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePatientRequestDto {
    private String username;
    private String email;
    private String fullName;
    private UserGender gender;
    private String phone_Number;
    private String address;
    private Instant dob;
}
