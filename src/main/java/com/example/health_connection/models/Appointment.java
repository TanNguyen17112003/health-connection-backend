package com.example.health_connection.models;

import java.time.Instant;

import org.springframework.lang.Nullable;

import com.example.health_connection.enums.AppointmentStatus;
import com.example.health_connection.enums.AppointmentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointment_id;
    private Long doctor_id;
    private Long patient_id;
    private Instant date;

    @Enumerated(EnumType.STRING)
    private AppointmentType type;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private String reason;
    private String notes;
    
    @Nullable
    private String cancel_reason;

    @Nullable
    private Long previous_appointment_id;
}
