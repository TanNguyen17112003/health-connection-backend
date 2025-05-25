package com.example.health_connection.controllers;
import com.example.health_connection.dtos.CreateMedicalRequestDto;
import com.example.health_connection.dtos.DeleteMedicalResponseDto;
import com.example.health_connection.dtos.MedicalRecordResponseDto;
import com.example.health_connection.models.User;
import com.example.health_connection.services.MedicalService;
import com.example.health_connection.utils.Constant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.MEDICAL_RECORD_CONTROLLER_PATH)

@Tag(name = "Medical Record", description="APIS for Medical Record Management")
public class MedicalController {
    private final MedicalService medicalService;
   
    @PostMapping("/{patientId}")
    @Operation(summary="Create medical record for patient following date")
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord(@PathVariable Long patientId, @RequestBody CreateMedicalRequestDto request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.createMedicalRecord(patientId, request, user));
    }

    @GetMapping("/list/{patientId}")
    @Operation(summary="Get list of medical records of a patient")
    public ResponseEntity<MedicalRecordResponseDto[]> getMedicalRecords(@PathVariable Long patientId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getRecords(patientId, user));
    }
    

    @GetMapping("/{id}")
    @Operation(summary="Get detail info of a medical record of a patient")
    public ResponseEntity<MedicalRecordResponseDto> getMedicalRecord(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.getRecord(id, user));
    }

    @PatchMapping("/{id}")
    @Operation(summary="Update information of a medical record for a patient from doctor")
    public ResponseEntity<MedicalRecordResponseDto> updateMedicalRecord(@PathVariable Long id, @RequestBody @Valid CreateMedicalRequestDto dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.updateMedicalRecord(id, dto, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Delete a medical record from a patient or persion")
    public ResponseEntity<DeleteMedicalResponseDto> deleteMedicalRecord(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(medicalService.deleteMedical(id, user));
    }
    
}
