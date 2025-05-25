package com.example.health_connection.controllers;

import com.example.health_connection.dtos.VaccinationRequestDto;
import com.example.health_connection.dtos.VaccinationResponseDto;
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
@RequestMapping(Constant.VACCINATION_CONTROLLER_PATH)
@Tag(name = "Vaccination", description = "APIs for Vaccination management")
public class VaccinationController {

    private final MedicalService medicalService;

    @PostMapping("/patients/{patientId}")
    @Operation(summary = "Create a new vaccination for a specific patient")
    public ResponseEntity<VaccinationResponseDto> createVaccination(
            @PathVariable Long patientId,
            @RequestBody @Valid VaccinationRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createVaccination(patientId, request, user));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get a list of all vaccinations for a specific patient")
    public ResponseEntity<VaccinationResponseDto[]> getVaccinationsForPatient(
            @PathVariable Long patientId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getVaccinations(patientId, user));
    }

    @GetMapping("/{vaccinationId}")
    @Operation(summary = "Get a specific vaccination by its ID")
    public ResponseEntity<VaccinationResponseDto> getVaccination(
            @PathVariable Long vaccinationId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getVaccination(vaccinationId, user));
    }

    @PatchMapping("/{vaccinationId}")
    @Operation(summary = "Update information of a specific vaccination")
    public ResponseEntity<VaccinationResponseDto> updateVaccination(
            @PathVariable Long vaccinationId,
            @RequestBody @Valid VaccinationRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updateVaccination(vaccinationId, request, user));
    }

    @DeleteMapping("/{vaccinationId}")
    @Operation(summary = "Delete a specific vaccination by its ID")
    public ResponseEntity<DeleteMedicalResponseDto> deleteVaccination(
            @PathVariable Long vaccinationId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deleteVaccination(vaccinationId, user));
    }
}
