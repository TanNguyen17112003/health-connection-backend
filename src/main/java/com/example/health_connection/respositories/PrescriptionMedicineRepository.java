package com.example.health_connection.respositories;

import com.example.health_connection.models.PrescriptionMedicine;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
}