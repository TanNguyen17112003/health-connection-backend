package com.example.health_connection.services;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.health_connection.dtos.AllergyRequestDto;
import com.example.health_connection.dtos.AllergyResponseDto;
import com.example.health_connection.dtos.CreateMedicalRequestDto;
import com.example.health_connection.dtos.DeleteMedicalResponseDto;
import com.example.health_connection.dtos.MedicalRecordResponseDto;
import com.example.health_connection.dtos.MedicalTreatmentRequestDto;
import com.example.health_connection.dtos.MedicalTreatmentResponseDto;
import com.example.health_connection.dtos.PastDiseaseRequestDto;
import com.example.health_connection.dtos.PastDiseaseResponseDto;
import com.example.health_connection.dtos.RelativeRequestDto;
import com.example.health_connection.dtos.RelativeResponseDto;
import com.example.health_connection.dtos.SurgicalHistoryRequestDto;
import com.example.health_connection.dtos.SurgicalHistoryResponseDto;
import com.example.health_connection.dtos.VaccinationRequestDto;
import com.example.health_connection.dtos.VaccinationResponseDto;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.exceptions.MedicalItemNotFoundException;
import com.example.health_connection.exceptions.MedicalRecordNotFoundException;
import com.example.health_connection.exceptions.UserNotAllowedException;
import com.example.health_connection.exceptions.UserNotFoundException;
import com.example.health_connection.models.Allergy;
import com.example.health_connection.models.MedicalRecord;
import com.example.health_connection.models.MedicalTreatment;
import com.example.health_connection.models.PastDisease;
import com.example.health_connection.models.Patient;
import com.example.health_connection.models.Relative;
import com.example.health_connection.models.SurgicalHistory;
import com.example.health_connection.models.User;
import com.example.health_connection.models.Vaccination;
import com.example.health_connection.respositories.AllergyRepository;
import com.example.health_connection.respositories.MedicalRecordRepository;
import com.example.health_connection.respositories.MedicalTreatmentRepository;
import com.example.health_connection.respositories.PastDiseaseRepository;
import com.example.health_connection.respositories.PatientRepository;
import com.example.health_connection.respositories.RelativeRepository;
import com.example.health_connection.respositories.SurgicalHistoryRepository;
import com.example.health_connection.respositories.VaccinationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MedicalService {
    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AllergyRepository allergyRepository;
    private final MedicalTreatmentRepository medicalTreatmentRepository;
    private final PastDiseaseRepository pastDiseaseRepository;
    private final RelativeRepository relativeRepository;
    private final SurgicalHistoryRepository surgicalHistoryRepository;
    private final VaccinationRepository vaccinationRepository;

    public MedicalRecordResponseDto createMedicalRecord(Long patientId, CreateMedicalRequestDto requestDto, User user) {
        if (user.getRole() == UserRole.PATIENT && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole() == UserRole.DOCTOR) {
            Patient foundPatient = patientRepository.findById(patientId).orElseThrow(() -> new UserNotFoundException());
            if (!foundPatient.getDoctor_id().equals(user.getUser_id())) {
                throw new UserNotAllowedException();
            }
        }
        Patient foundPatient = patientRepository.findById(patientId).orElseThrow(() -> new UserNotFoundException());
        MedicalRecord newRecord = new MedicalRecord();
        newRecord.setPatient(foundPatient);
        newRecord.setBlood_pressure(requestDto.getBlood_pressure());
        newRecord.setBlood_type(requestDto.getBlood_type());
        newRecord.setHeight(requestDto.getHeight());
        newRecord.setWeight(requestDto.getWeight());
        newRecord.setExtraInfo(requestDto.getExtraInfo());
        newRecord.setNotes(requestDto.getNotes());
        newRecord.setCreated_at(Instant.now());
        newRecord.setUpdated_at(Instant.now());
        newRecord = medicalRecordRepository.save(newRecord);
        return convertToMRResponseDto(newRecord);
    };

    public MedicalRecordResponseDto[] getRecords(Long patientId, User user) {
        Patient foundPatient = patientRepository.findById(patientId).orElseThrow(() -> new UserNotFoundException());
        if (user.getRole() == UserRole.PATIENT && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole() == UserRole.DOCTOR) {
            if (!foundPatient.getDoctor_id().equals(user.getUser_id())) {
                throw new UserNotAllowedException();
            }
        }
        List<MedicalRecord> records = medicalRecordRepository.findByPatient(foundPatient);
        return records.stream()
                .map(this::convertToMRResponseDto)
                .toArray(MedicalRecordResponseDto[]::new);
    }

    public MedicalRecordResponseDto getRecord(Long id, User user) {
        MedicalRecord foundRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException());
        Patient foundPatient = patientRepository.findById(foundRecord.getPatient().getUser_id())
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole() == UserRole.PATIENT && user.getUser_id() != foundPatient.getUser_id()) {
            throw new UserNotAllowedException();
        }
        if (user.getRole() == UserRole.DOCTOR && user.getUser_id() != foundPatient.getDoctor_id()) {
            throw new UserNotAllowedException();
        }
        return convertToMRResponseDto(foundRecord);
    }

    public MedicalRecordResponseDto updateMedicalRecord(Long id, CreateMedicalRequestDto request, User user) {
        MedicalRecord foundRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException());
        Patient foundPatient = patientRepository.findById(foundRecord.getPatient().getUser_id())
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole() == UserRole.PATIENT && user.getUser_id() != foundPatient.getUser_id()) {
            throw new UserNotAllowedException();
        }
        if (user.getRole() == UserRole.DOCTOR && user.getUser_id() != foundPatient.getDoctor_id()) {
            throw new UserNotAllowedException();
        }
        if (request.getBlood_pressure() != null) {
            foundRecord.setBlood_pressure(request.getBlood_pressure());
        }
        if (request.getBlood_type() != null) {
            foundRecord.setBlood_type(request.getBlood_type());
        }
        if (request.getHeight() != null) {
            foundRecord.setHeight(request.getHeight());
        }
        if (request.getWeight() != null) {
            foundRecord.setWeight(request.getWeight());
        }
        if (request.getExtraInfo() != null) {
            foundRecord.setExtraInfo(request.getExtraInfo());
        }
        if (request.getNotes() != null) {
            foundRecord.setNotes(request.getNotes());
        }
        foundRecord = medicalRecordRepository.save(foundRecord);
        return convertToMRResponseDto(foundRecord);
    }

    public DeleteMedicalResponseDto deleteMedical(Long id, User user) {
        MedicalRecord foundRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException());
        Patient foundPatient = patientRepository.findById(foundRecord.getPatient().getUser_id())
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole() == UserRole.PATIENT && user.getUser_id() != foundPatient.getUser_id()) {
            throw new UserNotAllowedException();
        }
        if (user.getRole() == UserRole.DOCTOR && user.getUser_id() != foundPatient.getDoctor_id()) {
            throw new UserNotAllowedException();
        }
        medicalRecordRepository.delete(foundRecord);
        return new DeleteMedicalResponseDto("Medical record has been successfully deleted", foundRecord.getMedicalRecord_id());
    }

    // API CRUD for allergy

    public AllergyResponseDto[] getAllergies(Long patientId, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT) && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        // For case when user is doctor
        if (user.getRole().equals(UserRole.DOCTOR) && !user.getUser_id().equals(foundPatient.getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        List<Allergy> allergies = this.allergyRepository.findByPatient(foundPatient);
        return allergies.stream()
                .map(this::covertToAllergyResponseDto)
                .toArray(AllergyResponseDto[]::new);
    }

    public AllergyResponseDto getAllergy(Long id, User user) {
        Allergy foundAllergy = this.allergyRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find tarrgetted allergy"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !foundAllergy.getPatient().getUser_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !foundAllergy.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return covertToAllergyResponseDto(foundAllergy);
    }

    public AllergyResponseDto createAllergy(Long patientId, AllergyRequestDto request) {
        Allergy newAllergy = new Allergy();
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        System.out.println(foundPatient.getUser_id());
        newAllergy.setAllergen(request.getAllergen());
        newAllergy.setSeverity(request.getSeverity());
        newAllergy.setNotes(request.getNotes());
        newAllergy.setPatient(foundPatient);
        newAllergy = this.allergyRepository.save(newAllergy);
        return covertToAllergyResponseDto(newAllergy);
    }

    public AllergyResponseDto updateAllergy(Long id, AllergyRequestDto request, User user) {
        Allergy foundAllergy = this.allergyRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted Allergy"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundAllergy.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundAllergy.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        if (request.getAllergen() != null) {
            foundAllergy.setAllergen(request.getAllergen());
        }
        if (request.getSeverity() != null) {
            foundAllergy.setSeverity(request.getSeverity());
        }
        if (request.getNotes() != null) {
            foundAllergy.setNotes(request.getNotes());
        }
        Allergy updatedAllergy = this.allergyRepository.save(foundAllergy);
        return covertToAllergyResponseDto(updatedAllergy);
    }

    public DeleteMedicalResponseDto deleteAllergy(Long id, User user) {
        Allergy foundAllergy = this.allergyRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted Allergy"));
        if (user.getRole().equals(UserRole.PATIENT)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundAllergy.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        this.allergyRepository.delete(foundAllergy);
        return new DeleteMedicalResponseDto( "You successfully delete the allergy", foundAllergy.getId());
    }

    // API CRUD for medical treatment
    public MedicalTreatmentResponseDto[] getMedicalTreatments(Long patientId, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT) && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR) && !foundPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        List<MedicalTreatment> medicalTreatments = this.medicalTreatmentRepository.findByPatient(foundPatient);
        return medicalTreatments.stream()
                .map(this::converToMTResponseDto)
                .toArray(MedicalTreatmentResponseDto[]::new);
    }

    public MedicalTreatmentResponseDto getMedicalTreatment(Long id, User user) {
        MedicalTreatment foundMedicalTreatment = this.medicalTreatmentRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted medical treatment"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !foundMedicalTreatment.getPatient().getUser_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !foundMedicalTreatment.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return converToMTResponseDto(foundMedicalTreatment);
    }

    public MedicalTreatmentResponseDto createMedicalTreatment(Long patientId, MedicalTreatmentRequestDto request,
            User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        MedicalTreatment newMedicalTreatment = new MedicalTreatment();
        newMedicalTreatment.setSymptoms(request.getSymptoms());
        newMedicalTreatment.setDiagnoses(request.getDiagnoses());
        newMedicalTreatment.setTreatments(request.getTreatments());
        newMedicalTreatment.setNotes(request.getNotes());
        newMedicalTreatment.setPatient(foundPatient);
        newMedicalTreatment = this.medicalTreatmentRepository.save(newMedicalTreatment);
        return converToMTResponseDto(newMedicalTreatment);
    }

    public MedicalTreatmentResponseDto updateMedicalTreatment(Long id, MedicalTreatmentRequestDto request, User user) {
        MedicalTreatment foundMedicalTreatment = this.medicalTreatmentRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted medical treatment"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundMedicalTreatment.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundMedicalTreatment.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }

        if (request.getSymptoms() != null) {
            foundMedicalTreatment.setSymptoms(request.getSymptoms());
        }
        if (request.getDiagnoses() != null) {
            foundMedicalTreatment.setDiagnoses(request.getDiagnoses());
        }
        if (request.getTreatments() != null) {
            foundMedicalTreatment.setTreatments(request.getTreatments());
        }
        if (request.getNotes() != null) {
            foundMedicalTreatment.setNotes(request.getNotes());
        }

        foundMedicalTreatment = this.medicalTreatmentRepository.save(foundMedicalTreatment);
        return converToMTResponseDto(foundMedicalTreatment);
    }

    public DeleteMedicalResponseDto deleteMedicalTreatment(Long id, User user) {
        MedicalTreatment foundMedicalTreatment = this.medicalTreatmentRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted medical treatment"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundMedicalTreatment.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundMedicalTreatment.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        this.medicalTreatmentRepository.delete(foundMedicalTreatment);
        return new DeleteMedicalResponseDto("You successfully delete the medical treatment", foundMedicalTreatment.getId());
    }

    // API CRUD for past disease
    public PastDiseaseResponseDto[] getPastDiseases(Long patientId, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT) && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR) && !foundPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        List<PastDisease> pastDiseases = this.pastDiseaseRepository.findByPatient(foundPatient);
        return pastDiseases.stream()
                .map(this::convertToPDResponseDto)
                .toArray(PastDiseaseResponseDto[]::new);
    }

    public PastDiseaseResponseDto getPastDisease(Long id, User user) {
        PastDisease foundPastDisease = this.pastDiseaseRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted past disease"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !foundPastDisease.getPatient().getUser_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !foundPastDisease.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return convertToPDResponseDto(foundPastDisease);
    }

    public PastDiseaseResponseDto createPastDisease(Long patientId, PastDiseaseRequestDto request, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        PastDisease newPastDisease = new PastDisease();
        newPastDisease.setName(request.getName());
        newPastDisease.setNotes(request.getNotes());
        newPastDisease.setPatient(foundPatient);
        newPastDisease = this.pastDiseaseRepository.save(newPastDisease);
        return convertToPDResponseDto(newPastDisease);
    }

    public PastDiseaseResponseDto updatePastDisease(Long id, PastDiseaseRequestDto request, User user) {
        PastDisease foundPastDisease = this.pastDiseaseRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted past disease"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundPastDisease.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundPastDisease.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        if (request.getName() != null) {
            foundPastDisease.setName(request.getName());
        }
        if (request.getNotes() != null) {
            foundPastDisease.setNotes(request.getNotes());
        }
        foundPastDisease = this.pastDiseaseRepository.save(foundPastDisease);
        return convertToPDResponseDto(foundPastDisease);
    }

    public DeleteMedicalResponseDto deletePastDisease(Long id, User user) {
        PastDisease foundPastDisease = this.pastDiseaseRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted past disease"));
        if (user.getRole().equals(UserRole.PATIENT)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundPastDisease.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        this.pastDiseaseRepository.delete(foundPastDisease);
        return new DeleteMedicalResponseDto("You successfully delete the past disease", foundPastDisease.getId());
    }

    // API CRUD for relative
    public RelativeResponseDto[] getRelatives(Long patientId, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT) && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR) && !foundPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        List<Relative> relatives = this.relativeRepository.findByPatient(foundPatient);
        return relatives.stream()
                .map(this::convertToRelativeResponseDto)
                .toArray(RelativeResponseDto[]::new);
    }

    public RelativeResponseDto getRelative(Long id, User user) {
        Relative foundRelative = this.relativeRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted relative"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !foundRelative.getPatient().getUser_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !foundRelative.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return convertToRelativeResponseDto(foundRelative);
    }

    public RelativeResponseDto createRelative(Long patientId, RelativeRequestDto request, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        Relative newRelative = new Relative();
        newRelative.setFullName(request.getFullName());
        newRelative.setRelationShip(request.getRelationShip());
        newRelative.setPhoneNumber(request.getPhoneNumber());
        newRelative.setPatient(foundPatient);
        newRelative = this.relativeRepository.save(newRelative);
        return convertToRelativeResponseDto(newRelative);
    }

    public RelativeResponseDto updateRelative(Long id, RelativeRequestDto request, User user) {
        Relative foundRelative = this.relativeRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted relative"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundRelative.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundRelative.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        if (request.getFullName() != null) {
            foundRelative.setFullName(request.getFullName());
        }
        if (request.getRelationShip() != null) {
            foundRelative.setRelationShip(request.getRelationShip());
        }
        if (request.getPhoneNumber() != null) {
            foundRelative.setPhoneNumber(request.getPhoneNumber());
        }
        foundRelative = this.relativeRepository.save(foundRelative);
        return convertToRelativeResponseDto(foundRelative);
    }

    public DeleteMedicalResponseDto deleteRelative(Long id, User user) {
        Relative foundRelative = this.relativeRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted relative"));
        if (user.getRole().equals(UserRole.PATIENT)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundRelative.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        this.relativeRepository.delete(foundRelative);
        return new DeleteMedicalResponseDto("You successfully delete the relative", foundRelative.getId());
    }

    // API CRUD for surgical history
    public SurgicalHistoryResponseDto[] getSurgicalHistories(Long patientId, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT) && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR) && !foundPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        List<SurgicalHistory> surgicalHistories = this.surgicalHistoryRepository.findByPatient(foundPatient);
        return surgicalHistories.stream()
                .map(this::convertToSHResponseDto)
                .toArray(SurgicalHistoryResponseDto[]::new);
    }

    public SurgicalHistoryResponseDto getSurgicalHistory(Long id, User user) {
        SurgicalHistory foundSurgicalHistory = this.surgicalHistoryRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted surgical history"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !foundSurgicalHistory.getPatient().getUser_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !foundSurgicalHistory.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return convertToSHResponseDto(foundSurgicalHistory);
    }

    public SurgicalHistoryResponseDto createSurgicalHistory(Long patientId, SurgicalHistoryRequestDto request,
            User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        SurgicalHistory newSurgicalHistory = new SurgicalHistory();
        newSurgicalHistory.setName(request.getName());
        newSurgicalHistory.setNotes(request.getNotes());
        newSurgicalHistory.setYear(request.getYear());
        newSurgicalHistory.setPatient(foundPatient);
        newSurgicalHistory = this.surgicalHistoryRepository.save(newSurgicalHistory);
        return convertToSHResponseDto(newSurgicalHistory);
    }

    public SurgicalHistoryResponseDto updateSurgicalHistory(Long id, SurgicalHistoryRequestDto request, User user) {
        SurgicalHistory foundSurgicalHistory = this.surgicalHistoryRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted surgical history"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundSurgicalHistory.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundSurgicalHistory.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        if (request.getName() != null) {
            foundSurgicalHistory.setName(request.getName());
        }
        if (request.getNotes() != null) {
            foundSurgicalHistory.setNotes(request.getNotes());
        }
        if (request.getYear() != null) {
            foundSurgicalHistory.setYear(request.getYear());
        }
        foundSurgicalHistory = this.surgicalHistoryRepository.save(foundSurgicalHistory);
        return convertToSHResponseDto(foundSurgicalHistory);
    }

    public DeleteMedicalResponseDto deleteSurgicalHistory(Long id, User user) {
        SurgicalHistory foundSurgicalHistory = this.surgicalHistoryRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted surgical history"));
        if (user.getRole().equals(UserRole.PATIENT)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundSurgicalHistory.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        this.surgicalHistoryRepository.delete(foundSurgicalHistory);
        return new DeleteMedicalResponseDto("You successfully delete the surgical history", foundSurgicalHistory.getId());
    }

    // API CRUD for vaccination
    public VaccinationResponseDto[] getVaccinations(Long patientId, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(UserRole.PATIENT) && !user.getUser_id().equals(patientId)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR) && !foundPatient.getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        List<Vaccination> vaccinations = this.vaccinationRepository.findByPatient(foundPatient);
        return vaccinations.stream()
                .map(this::convertToVaccinationResponseDto)
                .toArray(VaccinationResponseDto[]::new);
    }

    public VaccinationResponseDto getVaccination(Long id, User user) {
        Vaccination foundVaccination = this.vaccinationRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted vaccination"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !foundVaccination.getPatient().getUser_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !foundVaccination.getPatient().getDoctor_id().equals(user.getUser_id())) {
            throw new UserNotAllowedException();
        }
        return convertToVaccinationResponseDto(foundVaccination);
    }

    public VaccinationResponseDto createVaccination(Long patientId, VaccinationRequestDto request, User user) {
        Patient foundPatient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException());
        Vaccination newVaccination = new Vaccination();
        newVaccination.setName(request.getName());
        newVaccination.setDate(request.getDate());
        newVaccination.setYear(request.getYear());
        newVaccination.setPatient(foundPatient);
        newVaccination = this.vaccinationRepository.save(newVaccination);
        return convertToVaccinationResponseDto(newVaccination);
    }

    public VaccinationResponseDto updateVaccination(Long id, VaccinationRequestDto request, User user) {
        Vaccination foundVaccination = this.vaccinationRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted vaccination"));
        if (user.getRole().equals(UserRole.PATIENT)
                && !user.getUser_id().equals(foundVaccination.getPatient().getUser_id())) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundVaccination.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        if (request.getName() != null) {
            foundVaccination.setName(request.getName());
        }
        if (request.getDate() != null) {
            foundVaccination.setDate(request.getDate());
        }
        if (request.getYear() != null) {
            foundVaccination.setYear(request.getYear());
        }
        foundVaccination = this.vaccinationRepository.save(foundVaccination);
        return convertToVaccinationResponseDto(foundVaccination);
    }

    public DeleteMedicalResponseDto deleteVaccination(Long id, User user) {
        Vaccination foundVaccination = this.vaccinationRepository.findById(id)
                .orElseThrow(() -> new MedicalItemNotFoundException("Can't find the targeted surgical history"));
        if (user.getRole().equals(UserRole.PATIENT)) {
            throw new UserNotAllowedException();
        }
        if (user.getRole().equals(UserRole.DOCTOR)
                && !user.getUser_id().equals(foundVaccination.getPatient().getDoctor_id())) {
            throw new UserNotAllowedException();
        }
        this.vaccinationRepository.delete(foundVaccination);
        return new DeleteMedicalResponseDto("You successfully delete the surgical history", foundVaccination.getId());
    }

    // utils to convert model to response dto
    private AllergyResponseDto covertToAllergyResponseDto(Allergy allergy) {
        return new AllergyResponseDto(allergy.getId(), allergy.getAllergen(), allergy.getSeverity(),
                allergy.getNotes());
    }

    private MedicalTreatmentResponseDto converToMTResponseDto(MedicalTreatment mt) {
        return new MedicalTreatmentResponseDto(mt.getId(), mt.getSymptoms(), mt.getDiagnoses(), mt.getTreatments(),
                mt.getNotes());
    }

    private PastDiseaseResponseDto convertToPDResponseDto(PastDisease pd) {
        return new PastDiseaseResponseDto(pd.getId(), pd.getName(), pd.getNotes());
    }

    private RelativeResponseDto convertToRelativeResponseDto(Relative relative) {
        return new RelativeResponseDto(relative.getId(), relative.getFullName(), relative.getRelationShip(),
                relative.getPhoneNumber());
    }

    private SurgicalHistoryResponseDto convertToSHResponseDto(SurgicalHistory sh) {
        return new SurgicalHistoryResponseDto(sh.getId(), sh.getName(), sh.getNotes(), sh.getYear());
    }

    private VaccinationResponseDto convertToVaccinationResponseDto(Vaccination vaccination) {
        return new VaccinationResponseDto(vaccination.getId(), vaccination.getName(), vaccination.getDate(),
                vaccination.getYear());
    }

    private MedicalRecordResponseDto convertToMRResponseDto(MedicalRecord mr) {
        MedicalRecordResponseDto dto = new MedicalRecordResponseDto();
        dto.setId(mr.getMedicalRecord_id());
        dto.setBlood_pressure(mr.getBlood_pressure());
        dto.setBlood_type(mr.getBlood_type());
        dto.setWeight(mr.getWeight());
        dto.setHeight(mr.getHeight());
        dto.setExtraInfo(mr.getExtraInfo());
        dto.setNotes(mr.getNotes());
        return dto;
    }
}