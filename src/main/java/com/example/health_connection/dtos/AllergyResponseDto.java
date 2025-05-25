package com.example.health_connection.dtos;

import com.example.health_connection.enums.AllergySeverity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllergyResponseDto {
    private Long id;
    private String allergen;
    private AllergySeverity severity;
    private String notes;
}
