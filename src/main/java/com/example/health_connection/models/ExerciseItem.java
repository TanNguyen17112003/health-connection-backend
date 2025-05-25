package com.example.health_connection.models;

import java.time.Instant;

import org.springframework.lang.Nullable;

import com.example.health_connection.enums.ExerciseType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExerciseItem extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;
    
    private Long exercise_id;
    private String source_url;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    private String content;
    
    @Nullable
    private Integer duration;

    private Long base_exercise_id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id", insertable = false, updatable = false)
    private Exercise exercise;
}