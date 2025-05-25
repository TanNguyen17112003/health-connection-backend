package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.MedicalTreatment;
import com.example.health_connection.models.Patient;

import java.util.List;


@Repository
public interface MedicalTreatmentRepository extends JpaRepository<MedicalTreatment, Long>{
    List<MedicalTreatment> findByPatient(Patient patient);
} 
