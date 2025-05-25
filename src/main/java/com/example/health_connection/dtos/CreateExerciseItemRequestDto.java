package com.example.health_connection.dtos;

import org.springframework.lang.Nullable;

import com.example.health_connection.enums.ExerciseType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExerciseItemRequestDto {
    private String content;
    private String source_url;
    private Long exercise_id;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @Nullable
    private Integer duration;
}
