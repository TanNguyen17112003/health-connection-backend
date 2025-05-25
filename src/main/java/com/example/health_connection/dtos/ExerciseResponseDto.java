package com.example.health_connection.dtos;

import java.time.Instant;
import java.util.List;

import com.example.health_connection.enums.UserGender;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.models.Comment;
import com.example.health_connection.models.ExerciseItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ExerciseResponseDto {
    private Long exercise_id;
    private String description;
    private String title;
    private Instant created_at;
    private Instant updated_at;
    private List<ExerciseItem> items;
    private List<Comment> comments;
    private PatientInfo patientInfo;
    private DoctorInfo doctorInfo;

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