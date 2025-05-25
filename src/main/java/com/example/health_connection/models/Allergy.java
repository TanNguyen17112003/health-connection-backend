package com.example.health_connection.models;

import com.example.health_connection.enums.AllergySeverity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String allergen;

    @Enumerated(EnumType.STRING)
    private AllergySeverity severity;
    private String notes;

    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName="user_id")
    private Patient patient;
}


