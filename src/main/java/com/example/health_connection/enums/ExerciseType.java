package com.example.health_connection.enums;

public enum ExerciseType {
    VIDEO("VIDEO"),
    IMAGE("IMAGE");

    private final String ExerciseType;
    
    ExerciseType(String type) {
        this.ExerciseType = type;
    }

    public String getExerciseType() {
        return this.ExerciseType;
    }

}
