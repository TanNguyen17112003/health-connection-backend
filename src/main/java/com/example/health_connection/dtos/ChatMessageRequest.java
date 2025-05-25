package com.example.health_connection.dtos;

import com.example.health_connection.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {
    private String patientId;
    private String doctorId;
    private UserRole senderRole;
    private String content;
}
