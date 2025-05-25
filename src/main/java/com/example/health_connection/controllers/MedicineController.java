package com.example.health_connection.controllers;
import com.example.health_connection.dtos.CreateMedicineRequestDto;
import com.example.health_connection.dtos.DeleteMedicineResponseDto;
import com.example.health_connection.dtos.MedicineResponseDto;
import com.example.health_connection.dtos.UpdateMedicineRequestDto;
import com.example.health_connection.models.User;
import com.example.health_connection.services.MedicineService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.MEDICINE_CONTROLLER_PATH)
@Tag(name = "Medicine", description="APIS for medicine")
public class MedicineController {
    private final MedicineService medicineService;
    @GetMapping("")
    @Operation(summary = "Get a list of medicine")
    public ResponseEntity<MedicineResponseDto[]> getMedicines(@AuthenticationPrincipal User user) {
        MedicineResponseDto[] result = medicineService.getMedicines(user);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get detailed medicine based on Id attribute") 
    public ResponseEntity<MedicineResponseDto> getMedicine(@PathVariable Long id, @AuthenticationPrincipal User user) {
        MedicineResponseDto result = medicineService.getMedicine(id, user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    @Operation(summary = "Create a medicinet")
    public ResponseEntity<MedicineResponseDto> createMedicine(@RequestBody @Valid CreateMedicineRequestDto requestDto, @AuthenticationPrincipal User user) {
        MedicineResponseDto result = this.medicineService.createMedicine(requestDto, user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update description, title, medicineItem or simply add a new medicineitem")
    public ResponseEntity<MedicineResponseDto> updateMedicine(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody @Valid UpdateMedicineRequestDto requestDto) {
        MedicineResponseDto result = this.medicineService.updateMedicine(id, requestDto, user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Doctor delete a specified medicine")
    public ResponseEntity<DeleteMedicineResponseDto> deleteMedicine(@PathVariable Long id, @AuthenticationPrincipal User user) {
        DeleteMedicineResponseDto response = this.medicineService.deleteMedicine(id, user);
        return ResponseEntity.ok(response);
    }

}
