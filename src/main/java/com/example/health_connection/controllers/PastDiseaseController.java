package com.example.health_connection.controllers;

import com.example.health_connection.dtos.PastDiseaseRequestDto;
import com.example.health_connection.dtos.PastDiseaseResponseDto;
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
@RequestMapping(Constant.PAST_DISEASE_CONTROLLER_PATH)
@Tag(name = "Past Disease", description = "APIs for Past Disease management")
public class PastDiseaseController {

    private final MedicalService medicalService;

    @PostMapping("/patients/{patientId}")
    @Operation(summary = "Create a new past disease for a specific patient")
    public ResponseEntity<PastDiseaseResponseDto> createPastDisease(
            @PathVariable Long patientId,
            @RequestBody @Valid PastDiseaseRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createPastDisease(patientId, request, user));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get a list of all past diseases for a specific patient")
    public ResponseEntity<PastDiseaseResponseDto[]> getPastDiseasesForPatient(
            @PathVariable Long patientId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getPastDiseases(patientId, user));
    }

    @GetMapping("/{pastDiseaseId}")
    @Operation(summary = "Get a specific past disease by its ID")
    public ResponseEntity<PastDiseaseResponseDto> getPastDisease(
            @PathVariable Long pastDiseaseId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getPastDisease(pastDiseaseId, user));
    }

    @PatchMapping("/{pastDiseaseId}")
    @Operation(summary = "Update information of a specific past disease")
    public ResponseEntity<PastDiseaseResponseDto> updatePastDisease(
            @PathVariable Long pastDiseaseId,
            @RequestBody @Valid PastDiseaseRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updatePastDisease(pastDiseaseId, request, user));
    }

    @DeleteMapping("/{pastDiseaseId}")
    @Operation(summary = "Delete a specific past disease by its ID")
    public ResponseEntity<DeleteMedicalResponseDto> deletePastDisease(
            @PathVariable Long pastDiseaseId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deletePastDisease(pastDiseaseId, user));
    }
}
