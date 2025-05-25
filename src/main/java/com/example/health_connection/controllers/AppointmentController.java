package com.example.health_connection.controllers;
import com.example.health_connection.dtos.AppointmentResponseDto;
import com.example.health_connection.dtos.CancelAppointmentRequestDto;
import com.example.health_connection.dtos.CreateAppointmentRequest;
import com.example.health_connection.dtos.RescheduleAppointmentRequest;
import com.example.health_connection.models.User;
import com.example.health_connection.services.AppointmentService;
import com.example.health_connection.utils.Constant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.APPOINTMENT_CONTROLLER_PATH)
@Tag(name = "Appointment", description="APIS for appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    
    @GetMapping("")
    @Operation(summary = "Get list of appointments")
    public ResponseEntity<AppointmentResponseDto[]> getAppointments(@AuthenticationPrincipal User user) {
        AppointmentResponseDto[] result = appointmentService.getAppointments(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the detail appointment by id")
    public ResponseEntity<AppointmentResponseDto> getAppointment(@PathVariable Long id, @AuthenticationPrincipal User user) {
        AppointmentResponseDto result = appointmentService.getAppointmentById(id, user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    @Operation(summary = "Create a new appointment from both patient and admin")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody @Valid CreateAppointmentRequest request) {
       AppointmentResponseDto result = appointmentService.createAppointment(request);
       return ResponseEntity.ok(result);    
    }

    @PostMapping("/{id}/reschedule")
    @Operation(summary = "Create a follow_up appointment based on the previous appointment")
    public ResponseEntity<AppointmentResponseDto> rescheduleAppointment(@RequestBody @Valid RescheduleAppointmentRequest request, @PathVariable Long id) {
        AppointmentResponseDto result = appointmentService.rescheduleAppointment(request, id);
        return ResponseEntity.ok(result);    
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel an appointment due to the specified reasons from doctor")
    public ResponseEntity<AppointmentResponseDto> cancelAppointment(@RequestBody @Valid CancelAppointmentRequestDto request, @PathVariable Long id) {
        AppointmentResponseDto result = appointmentService.cancelAppointment(request, id);
        return ResponseEntity.ok(result);    
    }
    
    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete an appointment from doctor")
    public ResponseEntity<AppointmentResponseDto> completeAppointment(@PathVariable Long id) {
        AppointmentResponseDto result = appointmentService.completeAppointment(id);
        return ResponseEntity.ok(result);    
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Reject an appointment by ID")
    @ApiResponse(responseCode = "200", description = "Successfully rejected appointment")
    @ApiResponse(responseCode = "404", description = "Appointment not found")
    public ResponseEntity<AppointmentResponseDto> rejectAppointment(@PathVariable Long id) {
        AppointmentResponseDto result = appointmentService.rejectAppointment(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/accept")
    @Operation(summary = "Accept an appointment by ID")
    @ApiResponse(responseCode = "200", description = "Successfully accepted appointment")
    @ApiResponse(responseCode = "404", description = "Appointment not found")
    public ResponseEntity<AppointmentResponseDto> acceptAppointment(@PathVariable Long id) {
        AppointmentResponseDto result = appointmentService.acceptAppointment(id);
        return ResponseEntity.ok(result);
    }

    

}
