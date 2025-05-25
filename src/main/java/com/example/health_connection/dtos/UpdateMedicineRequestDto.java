package com.example.health_connection.dtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMedicineRequestDto {
    private String imageUrl;
    private String description;
    private String name;
    private Instant expired_at;
    private Instant manufactured_at;
    private Long usageNumber;
    private Long dose;
    private Long minutesBeforeEating;
    private Long minutesAfterEating;
}
