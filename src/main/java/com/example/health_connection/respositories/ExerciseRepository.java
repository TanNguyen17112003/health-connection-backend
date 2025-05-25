package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{

} 
