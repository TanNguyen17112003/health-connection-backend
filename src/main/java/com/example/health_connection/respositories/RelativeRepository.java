package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Patient;
import com.example.health_connection.models.Relative;
import java.util.List;


@Repository
public interface RelativeRepository extends JpaRepository<Relative, Long>{
    List<Relative> findByPatient(Patient patient);
} 
