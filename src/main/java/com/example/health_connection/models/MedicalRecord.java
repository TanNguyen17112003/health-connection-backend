package com.example.health_connection.models;


import org.springframework.lang.Nullable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MedicalRecord extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalRecord_id;
    private Integer blood_pressure;
    private String blood_type;
    private Integer height;
    private Integer weight;

    @Nullable
    private String extraInfo;

    @Nullable
    private String notes;
    
    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName="user_id")
    private Patient patient;
}
