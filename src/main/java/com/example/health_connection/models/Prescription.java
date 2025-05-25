package com.example.health_connection.models;

import java.time.Instant;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode; // Add this import

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescription_id;

    private Boolean is_deleted;

    @Nullable
    private String deleted_reason;

    private Instant start_date;
    private Instant end_date;

    @Nullable
    private Instant deleted_at;

    @Nullable
    private String notes;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude // <--- ADD THIS LINE
    private List<PrescriptionMedicine> prescriptionMedicines;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @EqualsAndHashCode.Exclude // <--- ADD THIS LINE
    private Patient patient;
}