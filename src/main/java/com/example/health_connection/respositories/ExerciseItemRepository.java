package com.example.health_connection.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Exercise;
import com.example.health_connection.models.ExerciseItem;
import java.util.List;


@Repository
public interface ExerciseItemRepository extends JpaRepository<ExerciseItem, Long>{
    List<ExerciseItem> findByExercise(Exercise exercise);
} 
