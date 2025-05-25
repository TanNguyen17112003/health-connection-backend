package com.example.health_connection.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateExerciseRequestDto {
    private String title;
    private String description;
    private Long patient_id;
}
