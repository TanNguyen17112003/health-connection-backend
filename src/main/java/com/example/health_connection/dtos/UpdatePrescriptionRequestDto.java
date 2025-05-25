package com.example.health_connection.dtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class UpdatePrescriptionRequestDto {
    private Instant start_date;
    private Instant end_date;
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
