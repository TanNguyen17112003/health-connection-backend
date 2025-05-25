package com.example.health_connection.dtos;

import com.example.health_connection.enums.UserGender;
import com.example.health_connection.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private UserRole role;
    private String fullName;
    private String userName;
    private String phone;
    private UserGender gender;
}
