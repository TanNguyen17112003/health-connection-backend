package com.example.health_connection.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.health_connection.models.ChatMessage;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByDoctorIdAndPatientIdOrderByTimestampAsc(Long doctorId, Long patientId);
}
