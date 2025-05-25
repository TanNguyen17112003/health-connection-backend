package com.example.health_connection.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurgicalHistoryResponseDto {
    private Long id;
    private String name;
    private String notes;
    private Long year;
}
