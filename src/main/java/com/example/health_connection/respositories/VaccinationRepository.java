package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Patient;
import com.example.health_connection.models.Vaccination;
import java.util.List;


@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long>{
    List<Vaccination> findByPatient(Patient patient);
} 
