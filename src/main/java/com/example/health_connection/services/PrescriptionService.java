package com.example.health_connection.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.health_connection.dtos.CreatePrescriptionRequestDto;
import com.example.health_connection.dtos.DeletePrescriptionRequestDto;
import com.example.health_connection.dtos.HandlePrescriptionResponseDto;
import com.example.health_connection.dtos.PrescriptionResponseDto;
import com.example.health_connection.dtos.UpdatePrescriptionRequestDto;
import com.example.health_connection.dtos.AppointmentResponseDto.PatientInfo;
import com.example.health_connection.dtos.PrescriptionResponseDto.PrescriptionDetail;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.exceptions.PrescriptionNotFoundException;
import com.example.health_connection.exceptions.UserNotAllowedException;
import com.example.health_connection.exceptions.UserNotFoundException;
import com.example.health_connection.models.Medicine;
import com.example.health_connection.models.Patient;
import com.example.health_connection.models.Prescription;
import com.example.health_connection.models.PrescriptionMedicine;
import com.example.health_connection.models.User;
import com.example.health_connection.respositories.MedicineRepository;
import com.example.health_connection.respositories.PatientRepository;
import com.example.health_connection.respositories.PrescriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionResponseDto createPrescription(Long patient_id, CreatePrescriptionRequestDto request,
            User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Patient foundPatient = patientRepository.findById(patient_id).orElseThrow(() -> new UserNotFoundException());
        if (!foundPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        Prescription prescription = new Prescription();
        prescription.setPatient(foundPatient);
        prescription.setIs_deleted(false);
        prescription.setDeleted_reason(null);
        prescription.setStart_date(request.getStart_date());
        prescription.setEnd_date(request.getEnd_date());
        prescription.setDeleted_at(null);
        prescription.setNotes(request.getNotes());
        prescription.setCreated_at(Instant.now());
        prescription.setUpdated_at(Instant.now());
        prescription = prescriptionRepository.save(prescription);
        List<PrescriptionMedicine> prescriptionMedicines = new ArrayList<>();
        for (CreatePrescriptionRequestDto.PrescriptionDetail detailDto : request.getDetails()) {
            Medicine medicine = medicineRepository.findById(detailDto.getMedicine_id())
                    .orElseThrow(() -> new RuntimeException("Medicine not found")); 

            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
            prescriptionMedicine.setPrescription(prescription);
            prescriptionMedicine.setMedicine(medicine);
            prescriptionMedicine.setUsageNumber(detailDto.getUsageNumber());
            prescriptionMedicine.setDose(detailDto.getDose());
            prescriptionMedicine.setMinutesBeforeEating(detailDto.getMinutesBeforeEating());
            prescriptionMedicine.setMinutesAfterEating(detailDto.getMinutesAfterEating());
            prescriptionMedicines.add(prescriptionMedicine);
        }
        prescription.setPrescriptionMedicines(prescriptionMedicines);
        prescriptionRepository.save(prescription);
        return convertToDto(prescription);
    }

    public PrescriptionResponseDto updatePrescription(Long id, UpdatePrescriptionRequestDto request, User user) {
        Prescription foundPrescription = prescriptionRepository.findById(id).orElseThrow(() -> new PrescriptionNotFoundException());
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        if (!foundPrescription.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (request.getStart_date() != null) {
            foundPrescription.setStart_date(request.getStart_date());
        }
        if (request.getEnd_date() != null) {
            foundPrescription.setEnd_date(request.getEnd_date());
        }
        if (request.getNotes() != null) {
            foundPrescription.setNotes(request.getNotes());
        }
        foundPrescription.setUpdated_at(Instant.now());
        foundPrescription = prescriptionRepository.save(foundPrescription);
        List<PrescriptionMedicine> prescriptionMedicines = new ArrayList<>();
        for (UpdatePrescriptionRequestDto.PrescriptionDetail detailDto : request.getDetails()) {
            Medicine medicine = medicineRepository.findById(detailDto.getMedicine_id())
                    .orElseThrow(() -> new RuntimeException("Medicine not found")); 

            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
            prescriptionMedicine.setPrescription(foundPrescription);
            prescriptionMedicine.setMedicine(medicine);
            prescriptionMedicine.setUsageNumber(detailDto.getUsageNumber());
            prescriptionMedicine.setDose(detailDto.getDose());
            prescriptionMedicine.setMinutesBeforeEating(detailDto.getMinutesBeforeEating());
            prescriptionMedicine.setMinutesAfterEating(detailDto.getMinutesAfterEating());
            prescriptionMedicines.add(prescriptionMedicine);
        }
        foundPrescription.setPrescriptionMedicines(prescriptionMedicines);
        prescriptionRepository.save(foundPrescription);
        return convertToDto(foundPrescription);
    }

    public PrescriptionResponseDto[] getPrescriptions(User user) {
        if (user.getRole().equals(UserRole.PATIENT)) {
            Patient patient = patientRepository.findById(user.getUser_id())
                    .orElseThrow(() -> new UserNotFoundException());

            List<Prescription> prescriptions = patient.getPrescriptions();

            return prescriptions.stream()
                    .map(this::convertToDto)
                    .toArray(PrescriptionResponseDto[]::new);
        } else {
            List<Patient> patients = patientRepository.findByDoctorId(user.getUser_id());
            Set<Prescription> foundPrescriptions = new HashSet<>();
            for (Patient patient : patients) {
                foundPrescriptions.addAll(patient.getPrescriptions());
            }
            return foundPrescriptions.stream()
                    .map(this::convertToDto)
                    .toArray(PrescriptionResponseDto[]::new);
        }
    }

    public PrescriptionResponseDto getPrescription(Long id, User user) {
        Prescription foundPrescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT)) {
            if (!foundPrescription.getPatient().getUser_id().equals(user.getUser_id())) {
                throw new UserNotAllowedException();
            }
        }
        if (user.getRole().equals(UserRole.DOCTOR)) {
            if (!foundPrescription.getPatient().getDoctor_id().equals(user.getUser_id())) {
                throw new UserNotAllowedException();
            }
        }
        return convertToDto(foundPrescription);
    }

    public PrescriptionResponseDto deletePrescription(Long id, DeletePrescriptionRequestDto request, User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Prescription foundPrescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException());
        if (!foundPrescription.getPatient().getDoctor_id().equals(user.getUser_id())
                || foundPrescription.getIs_deleted() == true) {
            throw new UserNotAllowedException();
        }
        foundPrescription.setIs_deleted(true);
        foundPrescription.setDeleted_at(Instant.now());
        System.out.println(request.getReason());
        foundPrescription.setDeleted_reason(request.getReason());
        foundPrescription = prescriptionRepository.save(foundPrescription);
        return convertToDto(foundPrescription);
    }

    public PrescriptionResponseDto recoverPrescription(Long id, User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Prescription foundPrescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException());
        if (!foundPrescription.getPatient().getDoctor_id().equals(user.getUser_id())
                || foundPrescription.getIs_deleted() == false) {
            throw new UserNotAllowedException();
        }
        foundPrescription.setIs_deleted(false);
        foundPrescription.setDeleted_reason(null);
        foundPrescription = prescriptionRepository.save(foundPrescription);
        System.out.println(foundPrescription.getDeleted_reason());
        return convertToDto(foundPrescription);
    }

    private PrescriptionResponseDto convertToDto(Prescription prescription) {
        PrescriptionResponseDto dto = new PrescriptionResponseDto();
        dto.setPrescription_id(prescription.getPrescription_id());
        dto.setIs_deleted(prescription.getIs_deleted());
        dto.setDeleted_reason(prescription.getDeleted_reason());
        dto.setStart_date(prescription.getStart_date());
        dto.setEnd_date(prescription.getEnd_date());
        dto.setDeleted_at(prescription.getDeleted_at());
        dto.setNotes(prescription.getNotes());

        if (prescription.getPatient() != null) {
            Patient patient = prescription.getPatient();
            dto.setPatient(new PatientInfo(
                    patient.getUser_id(),
                    patient.getUsername(),
                    patient.getEmail(),
                    patient.getFullName(),
                    patient.getAddress(),
                    patient.getDob(),
                    patient.getRole(),
                    patient.getGender(),
                    patient.getPhone_Number(),
                    patient.getDoctor_id()));
        }

        if (prescription.getPrescriptionMedicines() != null) {
            PrescriptionDetail[] details = prescription.getPrescriptionMedicines().stream()
                    .map(this::convertToPrescriptionDetailDto)
                    .toArray(PrescriptionDetail[]::new);
            dto.setDetails(details);
        }

        return dto;
    }

    private PrescriptionResponseDto.PrescriptionDetail convertToPrescriptionDetailDto(
            PrescriptionMedicine prescriptionMedicine) {
        PrescriptionResponseDto.PrescriptionDetail detailDto = new PrescriptionResponseDto.PrescriptionDetail();
        detailDto.setMedicine_id(prescriptionMedicine.getMedicine().getMedicine_id());
        detailDto.setUsageNumber(prescriptionMedicine.getUsageNumber());
        detailDto.setDose(prescriptionMedicine.getDose());
        detailDto.setMinutesBeforeEating(prescriptionMedicine.getMinutesBeforeEating());
        detailDto.setMinutesAfterEating(prescriptionMedicine.getMinutesAfterEating());
        return detailDto;
    }

}