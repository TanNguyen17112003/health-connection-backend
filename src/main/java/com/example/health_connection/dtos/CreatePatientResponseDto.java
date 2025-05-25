package com.example.health_connection.dtos;

import java.time.Instant;

import com.example.health_connection.enums.UserGender;
import com.example.health_connection.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreatePatientResponseDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private UserRole role;
    private UserGender gender;
    private String phone_Number;
    private String address;
    private Instant dob;
    private Long doctor_id;
}
