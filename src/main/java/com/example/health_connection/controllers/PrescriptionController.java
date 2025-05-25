package com.example.health_connection.controllers;
import com.example.health_connection.dtos.CreatePrescriptionRequestDto;
import com.example.health_connection.dtos.DeletePrescriptionRequestDto;
import com.example.health_connection.dtos.HandlePrescriptionResponseDto;
import com.example.health_connection.dtos.PrescriptionResponseDto;
import com.example.health_connection.dtos.UpdatePrescriptionRequestDto;
import com.example.health_connection.models.User;
import com.example.health_connection.services.PrescriptionService;
import com.example.health_connection.utils.Constant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.PRESCRIPTION_CONTROLLER_PATH)
@Tag(name = "Prescription", description="APIS for prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    @GetMapping("")
    @Operation(summary = "Get a list of prescription")
    public ResponseEntity<PrescriptionResponseDto[]> getPrescriptions(@AuthenticationPrincipal User user) {
        PrescriptionResponseDto[] result = prescriptionService.getPrescriptions(user);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get detailed prescription based on Id attribute") 
    public ResponseEntity<PrescriptionResponseDto> getPrescription(@PathVariable Long id, @AuthenticationPrincipal User user) {
        PrescriptionResponseDto result = prescriptionService.getPrescription(id, user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{patientId}")
    @Operation(summary = "Create an prescription and assigned for one patient")
    public ResponseEntity<PrescriptionResponseDto> createPrescription(@PathVariable Long patientId, @RequestBody @Valid CreatePrescriptionRequestDto requestDto, @AuthenticationPrincipal User user) {
        PrescriptionResponseDto result = this.prescriptionService.createPrescription(patientId, requestDto, user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update description, title, prescriptionItem or simply add a new prescriptionitem")
    public ResponseEntity<PrescriptionResponseDto> updatePrescription(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody @Valid UpdatePrescriptionRequestDto requestDto) {
        PrescriptionResponseDto result = this.prescriptionService.updatePrescription(id, requestDto, user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Doctor delete a specified prescription")
    public ResponseEntity<PrescriptionResponseDto> deletePrescription(@PathVariable Long id, @RequestBody DeletePrescriptionRequestDto request, @AuthenticationPrincipal User user) {
        PrescriptionResponseDto response = this.prescriptionService.deletePrescription(id, request, user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Doctor recover a specified prescription")
    public ResponseEntity<PrescriptionResponseDto> recoverPrescription(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.prescriptionService.recoverPrescription(id, user));
    }
}
