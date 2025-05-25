package com.example.health_connection.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.health_connection.dtos.ChatMessageRequest;
import com.example.health_connection.models.ChatMessage;
import com.example.health_connection.respositories.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatMessageRepository chatMessageRepository;

  public ChatMessage send(ChatMessageRequest message) {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setDoctorId(Long.parseLong(message.getDoctorId()));
    chatMessage.setPatientId(Long.parseLong(message.getPatientId()));
    chatMessage.setSenderRole(message.getSenderRole());
    chatMessage.setContent(message.getContent());
    chatMessage.setTimestamp(java.time.Instant.now());
    return chatMessageRepository.save(chatMessage);
  }

  public List<ChatMessage> getChatHistory(Long doctorId, Long patientId) {
    return chatMessageRepository.findByDoctorIdAndPatientIdOrderByTimestampAsc(doctorId, patientId);
  }
}
