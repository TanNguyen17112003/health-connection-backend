package com.example.health_connection.controllers;

import com.example.health_connection.models.ChatMessage;
import com.example.health_connection.dtos.ChatMessageRequest;
import com.example.health_connection.dtos.JwtResponseDto;
import com.example.health_connection.dtos.QrCodeResponseDto;
import com.example.health_connection.dtos.SigninDto;
import com.example.health_connection.services.AuthService;
import com.example.health_connection.services.ChatService;
import com.example.health_connection.services.QrCodeLoginService; // Import QrCodeLoginService
import com.example.health_connection.utils.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.AUTH_CONTROLLER_PATH)
@Tag(name = "Chat", description = "APIs for chat")
public class ChatController {
  private final ChatService chatService;
  @MessageMapping("/chat") 
  @SendTo("/topic/messages") 
  public ChatMessage send(ChatMessageRequest message) {
    return chatService.send(message);
  }

  @GetMapping("/chat/history")
  public ResponseEntity<List<ChatMessage>> getChatHistory(@RequestParam Long doctorId, @RequestParam Long patientId) {
      List<ChatMessage> chatHistory = chatService.getChatHistory(doctorId, patientId);
      return ResponseEntity.ok(chatHistory);
  }

}