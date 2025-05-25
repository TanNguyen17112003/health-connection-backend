package com.example.health_connection.controllers;

import com.example.health_connection.dtos.MedicalTreatmentRequestDto;
import com.example.health_connection.dtos.MedicalTreatmentResponseDto;
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
@RequestMapping(Constant.MEDICAL_TREATMENT_CONTROLLER_PATH)
@Tag(name = "Medical Treatment", description = "APIs for Medical Treatment management")
public class MedicalTreatmentController {

    private final MedicalService medicalService;

    @PostMapping("/patients/{patientId}")
    @Operation(summary = "Create a new medical treatment for a specific patient")
    public ResponseEntity<MedicalTreatmentResponseDto> createMedicalTreatment(
            @PathVariable Long patientId,
            @RequestBody @Valid MedicalTreatmentRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createMedicalTreatment(patientId, request, user));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get a list of all medical treatments for a specific patient")
    public ResponseEntity<MedicalTreatmentResponseDto[]> getMedicalTreatmentsForPatient(
            @PathVariable Long patientId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getMedicalTreatments(patientId, user));
    }

    @GetMapping("/{medicalTreatmentId}")
    @Operation(summary = "Get a specific medical treatment by its ID")
    public ResponseEntity<MedicalTreatmentResponseDto> getMedicalTreatment(
            @PathVariable Long medicalTreatmentId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getMedicalTreatment(medicalTreatmentId, user));
    }

    @PatchMapping("/{medicalTreatmentId}")
    @Operation(summary = "Update information of a specific medical treatment")
    public ResponseEntity<MedicalTreatmentResponseDto> updateMedicalTreatment(
            @PathVariable Long medicalTreatmentId,
            @RequestBody @Valid MedicalTreatmentRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updateMedicalTreatment(medicalTreatmentId, request, user));
    }

    @DeleteMapping("/{medicalTreatmentId}")
    @Operation(summary = "Delete a specific medical treatment by its ID")
    public ResponseEntity<DeleteMedicalResponseDto> deleteMedicalTreatment(
            @PathVariable Long medicalTreatmentId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deleteMedicalTreatment(medicalTreatmentId, user));
    }
}
