package com.example.health_connection.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelativeRequestDto {
    private String fullName;
    private String relationShip;
    private String phoneNumber;
}
