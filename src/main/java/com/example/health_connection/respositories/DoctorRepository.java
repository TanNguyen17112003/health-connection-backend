package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Doctor;
import com.example.health_connection.models.User;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    Doctor findByUser(User user);
} 
