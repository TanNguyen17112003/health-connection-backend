package com.example.health_connection.dtos;

import java.time.Instant;

import org.springframework.lang.Nullable;

import com.example.health_connection.enums.AppointmentType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAppointmentRequest {
    private Instant date;
    private Long patient_id;
    
    @Nullable
    private Long doctor_id;

    private AppointmentType type;
    private String reason;
    private String notes;
}
