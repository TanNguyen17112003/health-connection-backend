package com.example.health_connection.services;

import java.time.Instant;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.health_connection.dtos.AccountResponseDto;
import com.example.health_connection.dtos.CreatePatientRequestDto;
import com.example.health_connection.dtos.CreatePatientResponseDto;
import com.example.health_connection.dtos.DeleteAccountResponseDto;
import com.example.health_connection.dtos.PatientResponseDto;
import com.example.health_connection.dtos.UpdateInfoRequestDto;
import com.example.health_connection.dtos.UpdatePasswordRequestDto;
import com.example.health_connection.dtos.UpdatePasswordResponseDto;
import com.example.health_connection.dtos.AccountResponseDto.DoctorInfo;
import com.example.health_connection.dtos.AccountResponseDto.PatientInfo;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.exceptions.PasswordNotMatchException;
import com.example.health_connection.exceptions.UserNotAllowedException;
import com.example.health_connection.exceptions.UserNotFoundException;
import com.example.health_connection.models.Doctor;
import com.example.health_connection.models.Patient;
import com.example.health_connection.models.User;
import com.example.health_connection.respositories.DoctorRepository;
import com.example.health_connection.respositories.PatientRepository;
import com.example.health_connection.respositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreatePatientResponseDto createPatient(CreatePatientRequestDto request, User loggedInUser) {
        if (!loggedInUser.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }

        Patient newPatient = new Patient();
        newPatient.setEmail(request.getEmail());
        newPatient.setUsername(request.getUsername());
        newPatient.setFullName(request.getFullName());
        newPatient.setGender(request.getGender());
        newPatient.setRole(UserRole.PATIENT);
        newPatient.setCreated_at(Instant.now());
        newPatient.setUpdated_at(Instant.now());
        newPatient.setPhone_Number(request.getPhone_Number());
        newPatient.setPassword(passwordEncoder.encode(request.getUsername()));
        newPatient.setAddress(request.getAddress());
        newPatient.setDob(request.getDob());
        newPatient.setDoctor_id(loggedInUser.getUser_id());

        Patient savedPatient = this.patientRepository.save(newPatient);

        return new CreatePatientResponseDto(
                savedPatient.getUser_id(), 
                savedPatient.getUsername(),
                savedPatient.getEmail(),
                savedPatient.getFullName(),
                savedPatient.getRole(),
                savedPatient.getGender(),
                savedPatient.getPhone_Number(),
                savedPatient.getAddress(),
                savedPatient.getDob(),
                savedPatient.getDoctor_id()
        );
    }

    public AccountResponseDto getAccountInfo(User user) {
        Patient patient = this.patientRepository.findByUser(user);
        Long doctorId = patient.getDoctor_id();
        Doctor doctor = this.doctorRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException());
        return convertToDto(user, patient, doctor);
    } 

    public AccountResponseDto updateInfo(UpdateInfoRequestDto request, User user) {
        System.out.println("Chovy");
        System.out.println(request);
        if (request.getUserName() != null) {
            user.setUsername(request.getUserName());
        }
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone_Number(request.getPhone());
        }
        user = userRepository.save(user);
        AccountResponseDto responseDto = convertToDto(user, null, null); // Start with user info

        if (request.getAddress() != null || request.getDob() != null || request.getDoctor_id() != null) {
            Patient patient = (Patient) user;
            if (request.getAddress() != null) {
                patient.setAddress(request.getAddress());
            }
            if (request.getDob() != null) {
                patient.setDob(request.getDob());
            }
            if (request.getDoctor_id() != null) {
                patient.setDoctor_id(request.getDoctor_id());
            }
            patient = patientRepository.save(patient);
            responseDto = convertToDto(patient, patient, null);
        }

        if (request.getExperience() != null || request.getBiography() != null || request.getServices() != null || request.getSpecializations() != null) {
            System.out.println("success");
            Doctor doctor = (Doctor) user;
            if (request.getExperience() != null) {
                doctor.setExperience(request.getExperience());
            }
            if (request.getBiography() != null) {
                doctor.setBiography(request.getBiography());
            }
            if (request.getServices() != null) {
                doctor.setServices(request.getServices());
            }
            if (request.getSpecializations() != null) {
                doctor.setSpecializations(request.getSpecializations());
            }
            doctor = doctorRepository.save(doctor);
            responseDto = convertToDto(doctor, null, doctor);
        }

        return responseDto;
    }

    public PatientResponseDto[] getPatients(User user) {
        if (!user.getRole().equals(UserRole.DOCTOR)) {
            throw new UserNotAllowedException();
        }
        List<Patient> patients = this.patientRepository.findByDoctorId(user.getUser_id());
        return patients.stream().map(this::convertToPatientDto).toArray(PatientResponseDto[]::new);
    }


    public AccountResponseDto getUserById(Long id, User user) {
        User foundUser = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (foundUser.getRole() == user.getRole()) {
            throw new UserNotAllowedException();
        }
        // For case patient want to get Info of doctor
        if (user.getRole() == UserRole.PATIENT) {
            Patient loggedInUser = this.patientRepository.findByUser(user);
            if (!loggedInUser.getDoctor_id().equals(id)) {
                throw new UserNotAllowedException();
            }
            Doctor foundDoctor = doctorRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
            return convertToDto(foundUser, null, foundDoctor);
        }
        // For case doctor get info of detailed patient
        Patient targetPatient = patientRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (!targetPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return convertToDto(foundUser, targetPatient, null);
    }

    public UpdatePasswordResponseDto updatePassword(UpdatePasswordRequestDto request, User user) {
        String currentPasswordFromDatabase = user.getPassword();
        String currentPasswordInput = request.getCurrentPassword();

        if (!passwordEncoder.matches(currentPasswordInput, currentPasswordFromDatabase)) {
            throw new PasswordNotMatchException();
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPassword);
        user = this.userRepository.save(user);
        return new UpdatePasswordResponseDto("Successfully change password");
    }

    public DeleteAccountResponseDto deleteAccount(Long id, User user) {
        if (user.getRole() != UserRole.DOCTOR) {
            throw new UserNotAllowedException();
        }
        this.userRepository.deleteById(id);
        return new DeleteAccountResponseDto("Patient Delete Successfully", id);
    }

    private AccountResponseDto convertToDto(User user, Patient patient, Doctor doctor) {
        AccountResponseDto dto = new AccountResponseDto();
        dto.setId(user.getUser_id());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setFullName(user.getFullName());
        dto.setUserName(user.getUsername());
        dto.setPhone(user.getPhone_Number());
        dto.setGender(user.getGender());
        if (patient != null) {
            PatientInfo pInfo = new PatientInfo(patient.getAddress(), patient.getDob(), patient.getDoctor_id());
            dto.setPatientInfo(pInfo);
        }
        if (doctor != null) {
            DoctorInfo dInfo = new DoctorInfo(doctor.getUser_id(), doctor.getFullName(), doctor.getUsername(), doctor.getExperience(), doctor.getBiography(), doctor.getServices(), doctor.getSpecializations());
            System.out.println(dInfo);
            dto.setDoctorInfo(dInfo);
        }
        return dto;
    }

    private PatientResponseDto convertToPatientDto(Patient patient) {
        return new PatientResponseDto(
            patient.getUser_id(),
            patient.getUsername(),
            patient.getEmail(),
            patient.getFullName(),
            patient.getAddress(),
            patient.getDob(),
            patient.getRole(),
            patient.getGender(),
            patient.getPhone_Number(),
            patient.getDoctor_id()
        );
    }
  
}