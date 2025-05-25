package com.example.health_connection.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.health_connection.dtos.AppointmentResponseDto;
import com.example.health_connection.dtos.CancelAppointmentRequestDto;
import com.example.health_connection.dtos.CreateAppointmentRequest;
import com.example.health_connection.dtos.RescheduleAppointmentRequest;
import com.example.health_connection.enums.AppointmentStatus;
import com.example.health_connection.enums.AppointmentType;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.exceptions.AppointmentNotFoundException;
import com.example.health_connection.exceptions.UserNotFoundException;
import com.example.health_connection.models.Appointment;
import com.example.health_connection.models.Doctor;
import com.example.health_connection.models.Patient;
import com.example.health_connection.models.User;
import com.example.health_connection.respositories.AppointmentRepository;
import com.example.health_connection.respositories.DoctorRepository;
import com.example.health_connection.respositories.PatientRepository;
import com.example.health_connection.respositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public AppointmentResponseDto[] getAppointments(User user) {
        List<Appointment> allAppointments = this.appointmentRepository.findAll();
        UserRole role = user.getRole();
        List<Appointment> filteredAppointments;

        if (role == UserRole.DOCTOR) {
            filteredAppointments = allAppointments.stream()
                    .filter(appointment -> appointment.getDoctor_id().equals(user.getUser_id()))
                    .collect(Collectors.toList());
        } else { 
            filteredAppointments = allAppointments.stream()
                    .filter(appointment -> appointment.getPatient_id().equals(user.getUser_id()))
                    .collect(Collectors.toList());
        }

        return filteredAppointments.stream()
                .map(this::convertToDto) 
                .toArray(AppointmentResponseDto[]::new);
    }

    public AppointmentResponseDto getAppointmentById(Long appointment_id, User user) {
        Appointment foundAppointment = this.appointmentRepository.findById(appointment_id)
                .orElseThrow(() -> new AppointmentNotFoundException("Can not find the target appointment"));
        if (user.getUser_id() != foundAppointment.getDoctor_id() && user.getUser_id() != foundAppointment.getPatient_id()) {
            throw new AppointmentNotFoundException("You do not have permission to view this appointment");
        }
        return convertToDto(foundAppointment);
    }

    public AppointmentResponseDto createAppointment(CreateAppointmentRequest request) {
        Appointment newAppointment = new Appointment();
        newAppointment.setDate(request.getDate());
        newAppointment.setType(request.getType());
        newAppointment.setReason(request.getReason());
        newAppointment.setNotes(request.getNotes());
        newAppointment.setPatient_id(request.getPatient_id());
        newAppointment.setStatus(AppointmentStatus.PENDING);
        newAppointment.setCreated_at(Instant.now());
        newAppointment.setUpdated_at(Instant.now());
        if (request.getDoctor_id() != null) {
            newAppointment.setDoctor_id(request.getDoctor_id());
        } else {
            Patient patient = patientRepository.findById(request.getPatient_id())
                    .orElseThrow(() -> new UserNotFoundException());
            newAppointment.setDoctor_id(patient.getDoctor_id());
        }
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        return convertToDto(savedAppointment);
    }

    public AppointmentResponseDto rejectAppointment(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        Appointment appointment = appointmentOptional
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
        appointment.setStatus(AppointmentStatus.REJECTED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return convertToDto(updatedAppointment);
    }

    public AppointmentResponseDto completeAppointment(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        Appointment appointment = appointmentOptional
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return convertToDto(updatedAppointment);
    }

    public AppointmentResponseDto acceptAppointment(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        Appointment appointment = appointmentOptional
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
        appointment.setStatus(AppointmentStatus.ACCEPTED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return convertToDto(updatedAppointment);
    }

    public AppointmentResponseDto rescheduleAppointment(RescheduleAppointmentRequest request, Long appointment_id) {
        Appointment foundAppt = appointmentRepository.findById(appointment_id).orElseThrow(
                () -> new AppointmentNotFoundException("Appointment not found with ID: " + appointment_id));
        Appointment rescheduleAppointment = new Appointment();
        rescheduleAppointment.setDate(request.getDate());
        rescheduleAppointment.setPrevious_appointment_id(foundAppt.getAppointment_id());
        rescheduleAppointment.setType(AppointmentType.FOLLOW_UP);
        rescheduleAppointment.setDoctor_id(foundAppt.getDoctor_id());
        rescheduleAppointment.setPatient_id(foundAppt.getPatient_id());
        rescheduleAppointment.setStatus(AppointmentStatus.RESCHEDULED);
        rescheduleAppointment.setNotes(request.getNotes());
        rescheduleAppointment.setReason(request.getReason());
        rescheduleAppointment.setCreated_at(Instant.now());
        rescheduleAppointment.setUpdated_at(Instant.now());
        rescheduleAppointment = appointmentRepository.save(rescheduleAppointment);
        return convertToDto(rescheduleAppointment);
    }

    public AppointmentResponseDto cancelAppointment(CancelAppointmentRequestDto request, Long appointment_id) {
        Appointment cancelledAppointment = appointmentRepository.findById(appointment_id).orElseThrow(
                () -> new AppointmentNotFoundException("Appointment not found with ID: " + appointment_id));
        cancelledAppointment.setCancel_reason(request.getCancel_reason());
        cancelledAppointment.setStatus(AppointmentStatus.CANCELLED);
        cancelledAppointment = appointmentRepository.save(cancelledAppointment);
        return convertToDto(cancelledAppointment);
    }

    private AppointmentResponseDto convertToDto(Appointment appt) {
        User patient = userRepository.findById(appt.getPatient_id()).orElseThrow(() -> new UserNotFoundException());
        User doctor = userRepository.findById(appt.getDoctor_id()).orElseThrow(() -> new UserNotFoundException());
        Patient detailPatient = patientRepository.findByUser(patient);
        Doctor detailDoctor = doctorRepository.findByUser(doctor);
        AppointmentResponseDto.PatientInfo patientInfo = new AppointmentResponseDto.PatientInfo(
                patient.getUser_id(),
                patient.getUsername(),
                patient.getEmail(),
                patient.getFullName(),
                detailPatient.getAddress(),
                detailPatient.getDob(),
                patient.getRole(),
                patient.getGender(),
                patient.getPhone_Number(),
                detailPatient.getDoctor_id()

        );
        AppointmentResponseDto.DoctorInfo doctorInfo = new AppointmentResponseDto.DoctorInfo(
                doctor.getUser_id(),
                doctor.getUsername(),
                doctor.getEmail(),
                doctor.getFullName(),
                doctor.getRole(),
                doctor.getGender(),
                doctor.getPhone_Number(),
                detailDoctor.getExperience(),
                detailDoctor.getBiography(),
                detailDoctor.getServices(),
                detailDoctor.getSpecializations());
        return new AppointmentResponseDto(
                appt.getAppointment_id(),
                doctorInfo,
                patientInfo,
                appt.getDate(),
                appt.getType(),
                appt.getStatus(),
                appt.getReason(),
                appt.getNotes(),
                appt.getCancel_reason(),
                appt.getPrevious_appointment_id());
    }
}