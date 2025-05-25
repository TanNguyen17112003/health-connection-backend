package com.example.health_connection.controllers;
import com.example.health_connection.dtos.AllergyRequestDto;
import com.example.health_connection.dtos.AllergyResponseDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.ALLERGY_CONTROLLER_PATH)
@Tag(name = "Allergy", description = "APIS for allergy management")
public class AllergyController {
    private final MedicalService medicalService;

    @PostMapping("/patients/{patientId}")
    @Operation(summary = "Create a new allergy for a specific patient")
    public ResponseEntity<AllergyResponseDto> createAllergy(
            @PathVariable Long patientId,
            @RequestBody @Valid AllergyRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createAllergy(patientId, request));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get a list of all allergies for a specific patient")
    public ResponseEntity<AllergyResponseDto[]> getAllergiesForPatient(
            @PathVariable Long patientId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getAllergies(patientId, user));
    }

    @GetMapping("/{allergyId}")
    @Operation(summary = "Get a specific allergy by its ID")
    public ResponseEntity<AllergyResponseDto> getAllergy(
            @PathVariable Long allergyId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getAllergy(allergyId, user));
    }

    @PatchMapping("/{allergyId}")
    @Operation(summary = "Update information of a specific allergy")
    public ResponseEntity<AllergyResponseDto> updateAllergy(
            @PathVariable Long allergyId,
            @RequestBody @Valid AllergyRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updateAllergy(allergyId, request, user));
    }

    @DeleteMapping("/{allergyId}")
    @Operation(summary = "Delete a specific allergy by its ID")
    public ResponseEntity<DeleteMedicalResponseDto> deleteAllergy(
            @PathVariable Long allergyId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deleteAllergy(allergyId, user));
    }
}
