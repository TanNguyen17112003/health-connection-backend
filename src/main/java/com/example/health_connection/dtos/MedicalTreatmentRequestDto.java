package com.example.health_connection.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalTreatmentRequestDto {
    private String symptoms;
    private String diagnoses;
    private String treatments;
    private String notes;
}
