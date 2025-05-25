package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Allergy;
import com.example.health_connection.models.Patient;

import java.util.List;


@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long>{
    List<Allergy> findByPatient(Patient patient);
} 
