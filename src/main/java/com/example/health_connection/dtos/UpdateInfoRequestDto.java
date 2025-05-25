package com.example.health_connection.dtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateInfoRequestDto {
    private String userName;
    private String fullName;
    private String phone;

    // attributes related to patient
    private String address;
    private Instant dob;
    private Long doctor_id;

    // attributes related to doctor
    private Integer experience;
    private String biography;
    private String services;
    private String specializations;


}
