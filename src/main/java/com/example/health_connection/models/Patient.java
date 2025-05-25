package com.example.health_connection.models;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode; // Keep this
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class Patient extends User {
    private String address;
    private Instant dob;
    private Long doctor_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    // Exclude 'prescriptions' from equals and hashCode to break the circular reference
    @OneToMany(mappedBy = "patient")
    @EqualsAndHashCode.Exclude // <--- ADD THIS LINE
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient")
    @EqualsAndHashCode.Exclude // You might need to exclude other collections as well if they cause similar issues
    private Set<Allergy> allergies;

    @OneToMany(mappedBy = "patient")
    @EqualsAndHashCode.Exclude
    private List<PastDisease> pastDiseases;

    @OneToMany(mappedBy = "patient")
    @EqualsAndHashCode.Exclude
    private List<Vaccination> vaccinations;

    @OneToMany(mappedBy = "patient")
    @EqualsAndHashCode.Exclude
    private List<SurgicalHistory> surgicalHistories;

    @OneToMany(mappedBy = "patient")
    @EqualsAndHashCode.Exclude
    private List<MedicalTreatment> medicalTreatments;
}