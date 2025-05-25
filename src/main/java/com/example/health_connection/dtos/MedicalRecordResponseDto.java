package com.example.health_connection.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordResponseDto {
    private Long id;
    private Integer blood_pressure;
    private String blood_type;
    private Integer height;
    private Integer weight;
    private String extraInfo;
    private String notes;
    
}
