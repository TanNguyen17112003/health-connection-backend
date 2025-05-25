package com.example.health_connection.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeleteResponseDto {
    private String message;
    private Number id;
}
