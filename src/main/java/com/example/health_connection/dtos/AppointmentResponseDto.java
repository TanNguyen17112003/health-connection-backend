package com.example.health_connection.dtos;

import java.time.Instant;

import com.example.health_connection.enums.AppointmentStatus;
import com.example.health_connection.enums.AppointmentType;
import com.example.health_connection.enums.UserGender;
import com.example.health_connection.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentResponseDto {
    private Long appointment_id;
    private DoctorInfo doctor;
    private PatientInfo patient;
    private Instant date;
    private AppointmentType type;
    private AppointmentStatus status;
    private String reason;
    private String notes;
    private String cancel_reason;
    private Long previous_appointment_id;

    @Data
    @AllArgsConstructor
    public static class PatientInfo {
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

    @Data
    @AllArgsConstructor
    public static class DoctorInfo {
        private Long id; 
        private String username;
        private String email;
        private String fullName;
        private UserRole role;
        private UserGender gender;
        private String phoneNumber;
        private Integer experience;
        private String biography;
        private String services;
        private String specializations;
    }

}