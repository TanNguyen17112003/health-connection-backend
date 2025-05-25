package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.MedicalRecord;
import com.example.health_connection.models.Patient;

import java.util.List;


@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{
    List<MedicalRecord> findByPatient(Patient patient);
} 
