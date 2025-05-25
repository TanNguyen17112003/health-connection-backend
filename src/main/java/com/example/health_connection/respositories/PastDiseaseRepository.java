package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.PastDisease;
import com.example.health_connection.models.Patient;

import java.util.List;


@Repository
public interface PastDiseaseRepository extends JpaRepository<PastDisease, Long>{
    List<PastDisease> findByPatient(Patient patient);
} 
