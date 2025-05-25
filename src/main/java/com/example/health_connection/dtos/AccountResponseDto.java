package com.example.health_connection.dtos;

import java.time.Instant;

import org.springframework.lang.Nullable;

import com.example.health_connection.enums.UserGender;
import com.example.health_connection.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountResponseDto {
    private Long id;
    private String email;
    private UserRole role;
    private String fullName;
    private String userName;
    private String phone;
    private UserGender gender;

    @Nullable
    private PatientInfo patientInfo;

    @Nullable
    private DoctorInfo doctorInfo;

    @Data
    @AllArgsConstructor
    public static class PatientInfo {
        private String address;
        private Instant dob;
        private Long doctor_id;
    }

    @Data
    @AllArgsConstructor
    public static class DoctorInfo {
        private Long id;
        private String fullName;
        private String userName;
        private Integer experience;
        private String biography;
        private String services;
        private String specializations;
    }
}
