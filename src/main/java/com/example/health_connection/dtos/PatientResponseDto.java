package com.example.health_connection.dtos;

import java.time.Instant;

import com.example.health_connection.enums.UserGender;
import com.example.health_connection.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String address;
    private Instant dob;
    private UserRole role;
    private UserGender gender;
    private String phone_number;
    private Long doctor_id;
}
