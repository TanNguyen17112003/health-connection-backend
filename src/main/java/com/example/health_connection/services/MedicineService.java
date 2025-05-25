package com.example.health_connection.services;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.health_connection.dtos.CreateMedicineRequestDto;
import com.example.health_connection.dtos.DeleteMedicineResponseDto;
import com.example.health_connection.dtos.MedicineResponseDto;
import com.example.health_connection.dtos.UpdateMedicineRequestDto;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.exceptions.MedicineNotFoundException;
import com.example.health_connection.exceptions.UserNotAllowedException;
import com.example.health_connection.models.Medicine;
import com.example.health_connection.models.User;
import com.example.health_connection.respositories.MedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    
    public MedicineResponseDto[] getMedicines(User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        List<Medicine> medicines = this.medicineRepository.findAll();
        return medicines.stream().map(this::convertToDto).toArray(MedicineResponseDto[]::new);
    }

    public MedicineResponseDto getMedicine(Long id, User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Medicine foundMedicine = medicineRepository.findById(id).orElseThrow(() -> new MedicineNotFoundException());
        return convertToDto(foundMedicine);
    }

    public MedicineResponseDto createMedicine(CreateMedicineRequestDto request, User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Medicine newMedicine = new Medicine();
        newMedicine.setCreated_at(Instant.now());
        newMedicine.setUpdated_at(Instant.now());
        newMedicine.setImageUrl(request.getImageUrl());
        newMedicine.setDescription(request.getDescription());
        newMedicine.setName(request.getName());
        newMedicine.setExpired_at(request.getExpired_at());
        newMedicine.setManufactured_at(request.getManufactured_at());
        newMedicine.setUsageNumber(request.getUsageNumber());
        newMedicine.setDose(request.getDose());
        newMedicine.setMinutesAfterEating(request.getMinutesAfterEating());
        newMedicine.setMinutesBeforeEating(request.getMinutesBeforeEating());
        newMedicine = medicineRepository.save(newMedicine);
        return  convertToDto(newMedicine);
    }

    public MedicineResponseDto updateMedicine(Long id, UpdateMedicineRequestDto request,  User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Medicine foundMedicine = medicineRepository.findById(id).orElseThrow(() -> new MedicineNotFoundException());
        if (request.getImageUrl() != null) {
            foundMedicine.setImageUrl(request.getImageUrl());
        }
        if (request.getDescription() != null) {
            foundMedicine.setDescription(request.getDescription());
        }
        if (request.getName() != null) {
            foundMedicine.setName(request.getName());
        }
        if (request.getExpired_at() != null) {
            foundMedicine.setExpired_at(request.getExpired_at());
        }
        if (request.getManufactured_at() != null) {
            foundMedicine.setManufactured_at(request.getManufactured_at());
        }
        if (request.getUsageNumber() != null) {
            foundMedicine.setUsageNumber(request.getUsageNumber());
        }
        if (request.getDose() != null) {
            foundMedicine.setDose(request.getDose());
        }
        if (request.getMinutesAfterEating() != null) {
            foundMedicine.setMinutesAfterEating(request.getMinutesAfterEating());
        }
        if (request.getMinutesBeforeEating() != null) {
            foundMedicine.setMinutesBeforeEating(request.getMinutesBeforeEating());
        }
        foundMedicine.setUpdated_at(Instant.now());
        foundMedicine = medicineRepository.save(foundMedicine);
        return convertToDto(foundMedicine);
    }

    public DeleteMedicineResponseDto deleteMedicine(Long id, User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        Medicine foundMedicine = medicineRepository.findById(id).orElseThrow(() -> new MedicineNotFoundException());
        medicineRepository.delete(foundMedicine);
        return new DeleteMedicineResponseDto("Medicine deleted successfully");
    }

    public MedicineResponseDto convertToDto(Medicine medicine) {
        return new MedicineResponseDto(
            medicine.getMedicine_id(),
            medicine.getImageUrl(),
            medicine.getDescription(),
            medicine.getName(),
            medicine.getExpired_at(),
            medicine.getManufactured_at(),
            medicine.getUsageNumber(),
            medicine.getDose(),
            medicine.getMinutesBeforeEating(),
            medicine.getMinutesAfterEating()
        );
    }
}