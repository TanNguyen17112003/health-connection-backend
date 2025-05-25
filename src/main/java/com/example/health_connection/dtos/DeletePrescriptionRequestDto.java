package com.example.health_connection.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeletePrescriptionRequestDto {
    String reason;
}
