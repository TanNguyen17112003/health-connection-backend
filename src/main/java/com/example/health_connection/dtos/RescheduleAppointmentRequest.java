package com.example.health_connection.dtos;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RescheduleAppointmentRequest {
    private Instant date;
    private String reason;
    private String notes;
}
