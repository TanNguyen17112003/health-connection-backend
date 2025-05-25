package com.example.health_connection.controllers;
import com.example.health_connection.dtos.AccountResponseDto;
import com.example.health_connection.dtos.CreatePatientRequestDto;
import com.example.health_connection.dtos.CreatePatientResponseDto;
import com.example.health_connection.dtos.DeleteAccountResponseDto;
import com.example.health_connection.dtos.PatientResponseDto;
import com.example.health_connection.dtos.UpdateInfoRequestDto;
import com.example.health_connection.dtos.UpdatePasswordRequestDto;
import com.example.health_connection.dtos.UpdatePasswordResponseDto;
import com.example.health_connection.models.Patient;
import com.example.health_connection.models.User;
import com.example.health_connection.services.UserService;
import com.example.health_connection.utils.Constant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.USER_CONTROLLER_PATH)

@Tag(name = "User", description="APIS for user management")
public class UserController {
    private final UserService userService;
   
    @PostMapping("/create-patient")
    @Operation(summary="Doctor create a patient with basic info")
    public ResponseEntity<CreatePatientResponseDto> createPatient(@RequestBody @Valid CreatePatientRequestDto request, @AuthenticationPrincipal User user) {
    CreatePatientResponseDto responseDto = userService.createPatient(request, user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                            .path("/{id}")
                                            .buildAndExpand(responseDto.getId()) // Assuming your DTO has an getId()
                                            .toUri();
    return ResponseEntity.created(location).body(responseDto);
}

    @GetMapping("/account")
    @Operation(summary="Get personal information from the curren user based on role")
    public ResponseEntity<AccountResponseDto> getAccountInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getAccountInfo(user));
    }
    

    @PatchMapping("/account")
    @Operation(summary="Update information of the current user")
    public ResponseEntity<AccountResponseDto> updateInfo(@RequestBody @Valid UpdateInfoRequestDto request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updateInfo(request, user));
    }

    @GetMapping("/patients")
    @Operation(summary="Get a list of patients of a doctor")
    public ResponseEntity<PatientResponseDto[]> getPatients(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getPatients(user));
    }

    @GetMapping("/{id}")
    @Operation(summary="Get information of user based on role")
    public ResponseEntity<AccountResponseDto> getDetailPatient(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserById(id, user));
    }

    @PatchMapping("/password")
    @Operation(summary="Update password for the current user")
    public ResponseEntity<UpdatePasswordResponseDto> updatePassword(@RequestBody @Valid UpdatePasswordRequestDto request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updatePassword(request, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Doctor delete the patient account due to specifi errors")
    public ResponseEntity<DeleteAccountResponseDto> deleteAccount(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.deleteAccount(id, user));
    }

    
}
