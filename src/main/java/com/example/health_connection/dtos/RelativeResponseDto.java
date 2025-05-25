package com.example.health_connection.dtos;

import com.example.health_connection.enums.AllergySeverity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelativeResponseDto {
    private Long id;
    private String fullName;
    private String relationShip;
    private String phoneNumber;
}
