package com.example.health_connection.dtos;

import java.time.Instant;

import com.example.health_connection.dtos.AppointmentResponseDto.PatientInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PrescriptionResponseDto {
    private Long prescription_id;
    private PatientInfo patient;
    private Boolean is_deleted;
    private String deleted_reason;
    private Instant start_date;
    private Instant end_date;
    private Instant deleted_at;
    private String notes;
    private PrescriptionDetail[] details;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrescriptionDetail {
        private Long medicine_id;
        private Long usageNumber;
        private Long dose;
        private Long minutesBeforeEating;
        private Long minutesAfterEating;
    }
}
