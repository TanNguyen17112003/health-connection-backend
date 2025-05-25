package com.example.health_connection.controllers;

import com.example.health_connection.dtos.JwtResponseDto;
import com.example.health_connection.dtos.QrCodeResponseDto;
import com.example.health_connection.dtos.SigninDto;
import com.example.health_connection.services.AuthService;
import com.example.health_connection.services.QrCodeLoginService; // Import QrCodeLoginService
import com.example.health_connection.utils.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.AUTH_CONTROLLER_PATH)
@Tag(name = "Authentication", description = "APIs for authentication")
public class AuthController {

    private final AuthService authService;
    private final QrCodeLoginService qrCodeLoginService; 

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody @Valid SigninDto signinDto) {
        JwtResponseDto userWithToken = authService.signIn(signinDto);
        return ResponseEntity.ok(userWithToken);
    }

    @GetMapping("/qr-code/generate")
    public ResponseEntity<QrCodeResponseDto> handleGenerateQrCode() {
        return ResponseEntity.ok(qrCodeLoginService.generateQrCode());
    }

    // @MessageMapping("/qr-code/scan")
    // public void handleQrCodeScan(Message<String> message, @Header("simpSessionId") String sessionId) {
    //     String uuid = message.getPayload();
    //     qrCodeLoginService.validateQrCode(uuid, sessionId);
    // }
}