package com.example.health_connection.dtos;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateExerciseRequestDto {
    private String description;
    private String title;
    private ExerciseItemInfo[] exerciseItemInfo;

    @Data
    @AllArgsConstructor
    public static class ExerciseItemInfo {
        private Long item_id;
        private String source_url;
        @Nullable
        private Integer duration;
        private String content;
    }
}
