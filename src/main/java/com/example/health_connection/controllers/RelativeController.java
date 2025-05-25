package com.example.health_connection.controllers;

import com.example.health_connection.dtos.RelativeRequestDto;
import com.example.health_connection.dtos.RelativeResponseDto;
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
@RequestMapping(Constant.RELATIVE_CONTROLLER_PATH)
@Tag(name = "Relative", description = "APIs for Relative management")
public class RelativeController {

    private final MedicalService medicalService;

    @PostMapping("/patients/{patientId}")
    @Operation(summary = "Create a new relative for a specific patient")
    public ResponseEntity<RelativeResponseDto> createRelative(
            @PathVariable Long patientId,
            @RequestBody @Valid RelativeRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createRelative(patientId, request, user));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get a list of all relatives for a specific patient")
    public ResponseEntity<RelativeResponseDto[]> getRelativesForPatient(
            @PathVariable Long patientId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getRelatives(patientId, user));
    }

    @GetMapping("/{relativeId}")
    @Operation(summary = "Get a specific relative by its ID")
    public ResponseEntity<RelativeResponseDto> getRelative(
            @PathVariable Long relativeId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getRelative(relativeId, user));
    }

    @PatchMapping("/{relativeId}")
    @Operation(summary = "Update information of a specific relative")
    public ResponseEntity<RelativeResponseDto> updateRelative(
            @PathVariable Long relativeId,
            @RequestBody @Valid RelativeRequestDto request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updateRelative(relativeId, request, user));
    }

    @DeleteMapping("/{relativeId}")
    @Operation(summary = "Delete a specific relative by its ID")
    public ResponseEntity<DeleteMedicalResponseDto> deleteRelative(
            @PathVariable Long relativeId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deleteRelative(relativeId, user));
    }
}
