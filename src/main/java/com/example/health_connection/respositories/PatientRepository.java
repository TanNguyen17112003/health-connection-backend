package com.example.health_connection.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Patient;
import com.example.health_connection.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
    Patient findByUser(User user);

    @Query("SELECT p FROM Patient p WHERE p.doctor_id = :doctorId")
    List<Patient> findByDoctorId(@Param("doctorId") Long doctorId);
}