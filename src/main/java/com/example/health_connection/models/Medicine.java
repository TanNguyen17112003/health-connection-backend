package com.example.health_connection.models;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicine_id;
    private String imageUrl;
    private String description;
    private String name;
    private Instant expired_at;
    private Instant manufactured_at;
    private Long usageNumber;
    private Long dose;
    private Long minutesBeforeEating;
    private Long minutesAfterEating;
    @OneToMany(mappedBy = "medicine")
    private List<PrescriptionMedicine> prescriptionMedicines;
}