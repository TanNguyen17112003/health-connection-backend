package com.example.health_connection.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class MultipleFileUploadResponseDto {
    private List<String> urls;
}
