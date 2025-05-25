package com.example.health_connection.services;

import com.example.health_connection.dtos.QrCodeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class QrCodeLoginService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public QrCodeResponseDto generateQrCode() {
        String uuid = UUID.randomUUID().toString();
        messagingTemplate.convertAndSend("/qr/generate-code", uuid);
        return new QrCodeResponseDto(uuid);
    }

    // public void validateQrCode(String uuid, String sessionId) {
    //     String device1SessionId = qrCodeToSessionId.get(uuid);
    //     if (device1SessionId != null) {
    //         String token = "example_token_" + uuid; 
    //         messagingTemplate.convertAndSendToUser(device1SessionId, "/qr/login-success", token);
    //         qrCodeToSessionId.remove(uuid);
    //     } else {
    //          messagingTemplate.convertAndSendToUser(sessionId, "/qr/login-failed", "QR code không hợp lệ");
    //     }
    // }
   
}

