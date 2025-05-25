package com.example.health_connection.dtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VaccinationRequestDto {
    private String name;
    private Instant date;
    private Long year;
}
