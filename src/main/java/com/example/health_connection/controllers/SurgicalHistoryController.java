package com.example.health_connection.controllers;

import com.example.health_connection.dtos.SurgicalHistoryRequestDto;
import com.example.health_connection.dtos.SurgicalHistoryResponseDto;
import com.example.health_connection.dtos.DeleteMedicalResponseDto;
import com.example.health_connection.models.User;
import com.example.health_connection.services.MedicalService;
import com.example.health_connection.utils.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.SURGICAL_HISTORY_CONTROLLER_PATH)
@Tag(name = "Surgical History", description = "APIs for Surgical History management")
public class SurgicalHistoryController {

    private final MedicalService medicalService;

    @PostMapping("/patients/{patientId}")
    @Operation(summary = "Create a new surgical history for a specific patient")
    public ResponseEntity<SurgicalHistoryResponseDto> createSurgicalHistory(
            @PathVariable Long patientId,
            @RequestBody @Valid SurgicalHistoryRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createSurgicalHistory(patientId, request, user));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get a list of all surgical histories for a specific patient")
    public ResponseEntity<SurgicalHistoryResponseDto[]> getSurgicalHistoriesForPatient(
            @PathVariable Long patientId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getSurgicalHistories(patientId, user));
    }

    @GetMapping("/{surgicalHistoryId}")
    @Operation(summary = "Get a specific surgical history by its ID")
    public ResponseEntity<SurgicalHistoryResponseDto> getSurgicalHistory(
            @PathVariable Long surgicalHistoryId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getSurgicalHistory(surgicalHistoryId, user));
    }

    @PatchMapping("/{surgicalHistoryId}")
    @Operation(summary = "Update information of a specific surgical history")
    public ResponseEntity<SurgicalHistoryResponseDto> updateSurgicalHistory(
            @PathVariable Long surgicalHistoryId,
            @RequestBody @Valid SurgicalHistoryRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updateSurgicalHistory(surgicalHistoryId, request, user));
    }

    @DeleteMapping("/{surgicalHistoryId}")
    @Operation(summary = "Delete a specific surgical history by its ID")
    public ResponseEntity<DeleteMedicalResponseDto> deleteSurgicalHistory(
            @PathVariable Long surgicalHistoryId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deleteSurgicalHistory(surgicalHistoryId, user));
    }
}
