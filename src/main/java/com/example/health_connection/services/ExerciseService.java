package com.example.health_connection.services;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.health_connection.dtos.CreateCommentRequestDto;
import com.example.health_connection.dtos.CreateExerciseItemRequestDto;
import com.example.health_connection.dtos.CreateExerciseRequestDto;
import com.example.health_connection.dtos.DeleteResponseDto;
import com.example.health_connection.dtos.ExerciseResponseDto;
import com.example.health_connection.dtos.ReplyExerciseItemRequestDto;
import com.example.health_connection.dtos.UpdateExerciseRequestDto;
import com.example.health_connection.enums.UserRole;
import com.example.health_connection.exceptions.ExerciseNotAllowedException;
import com.example.health_connection.exceptions.ExerciseNotFoundException;
import com.example.health_connection.exceptions.UserNotFoundException;
import com.example.health_connection.models.Comment;
import com.example.health_connection.models.Doctor;
import com.example.health_connection.models.Exercise;
import com.example.health_connection.models.ExerciseItem;
import com.example.health_connection.models.Patient;
import com.example.health_connection.models.User;
import com.example.health_connection.respositories.CommentRepository;
import com.example.health_connection.respositories.DoctorRepository;
import com.example.health_connection.respositories.ExerciseItemRepository;
import com.example.health_connection.respositories.ExerciseRepository;
import com.example.health_connection.respositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ExerciseService{
    private final ExerciseRepository exerciseRepository;
    private final ExerciseItemRepository exerciseItemRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public ExerciseResponseDto[] getExercises(User user) {
        List<Exercise> filteredExercises = this.exerciseRepository.findAll().stream().filter(exercise -> (exercise.getDoctor_id() == user.getUser_id() || exercise.getPatient_id() == user.getUser_id())).collect(Collectors.toList());
        return filteredExercises.stream().map(this::convertDto).toArray(ExerciseResponseDto[]::new);
    }

    public ExerciseResponseDto getExercise(Long id, User user) {
        Exercise foundExercise = this.exerciseRepository.findById(id).orElseThrow(() -> new ExerciseNotFoundException("Not found this exercise"));
        if (!foundExercise.getDoctor_id().equals(user.getUser_id()) && !foundExercise.getPatient_id().equals(user.getUser_id())) {
            throw new ExerciseNotAllowedException("You are not allowed to get info of this exercise");
        }
        return convertDto(foundExercise);
    }

    public ExerciseResponseDto createExercise(CreateExerciseRequestDto request, User user) {
        if (user.getRole() != UserRole.DOCTOR){
            throw new UserNotFoundException();
        }
        Exercise newExercise = new Exercise();
        newExercise.setDoctor_id(user.getUser_id());
        newExercise.setTitle(request.getTitle());
        newExercise.setDescription(request.getDescription());
        newExercise.setPatient_id(request.getPatient_id());
        newExercise.setCreated_at(Instant.now());
        newExercise.setUpdated_at(Instant.now());
        newExercise = this.exerciseRepository.save(newExercise);
        return convertDto(newExercise);
    }

    public ExerciseItem createExerciseItem(CreateExerciseItemRequestDto request, User user) {
        if (user.getRole() != UserRole.DOCTOR){
            throw new UserNotFoundException();
        }
        ExerciseItem newExerciseItem = new ExerciseItem();
        newExerciseItem.setContent(request.getContent());
        newExerciseItem.setSource_url(request.getSource_url());
        newExerciseItem.setExercise_id(request.getExercise_id());
        newExerciseItem.setType(request.getType());
        newExerciseItem.setDuration(request.getDuration());
        newExerciseItem.setCreated_at(Instant.now());
        newExerciseItem.setUpdated_at(Instant.now());
        newExerciseItem = this.exerciseItemRepository.save(newExerciseItem);
        return newExerciseItem;
    }

    public Comment createCommentonExercise(Long id, User user, CreateCommentRequestDto request) {
        if (user.getRole() != UserRole.DOCTOR) {
            throw new UserNotFoundException();
        }
        Comment newComment = new Comment();
        newComment.setContent(request.getContent());
        newComment.setExercise_id(id);
        newComment.setCreated_at(Instant.now());
        newComment.setUpdated_at(Instant.now());
        newComment = this.commentRepository.save(newComment);
        return newComment;
    }

    public ExerciseItem replyExerciseItem(Long id, User user, ReplyExerciseItemRequestDto request) {
        if (user.getRole() != UserRole.PATIENT) {
            throw new UserNotFoundException();
        }
        ExerciseItem newItem = new ExerciseItem();
        newItem.setBase_exercise_id(request.getBase_exercise_id());
        newItem.setContent(request.getContent());
        newItem.setExercise_id(id);
        newItem.setSource_url(request.getSource_url());
        newItem.setType(request.getType());
        newItem.setDuration(request.getDuration());
        newItem.setCreated_at(Instant.now());
        newItem.setUpdated_at(Instant.now());
        newItem = this.exerciseItemRepository.save(newItem);
        return newItem;
    }
    
    @Transactional
    public DeleteResponseDto deleteExercise(Long id, User user) {
        Exercise foundExercise = this.exerciseRepository.findById(id).orElseThrow(() -> new ExerciseNotFoundException("Can not find targeted exercise"));
        if (user.getRole() != UserRole.DOCTOR) {
            throw new Error("You can not delete this exercise");
        }
        this.exerciseRepository.delete(foundExercise);
        return new DeleteResponseDto("Delete successfully", id);
    }

    public ExerciseResponseDto updateExercise(Long id, User user, UpdateExerciseRequestDto request) {
        Exercise foundExercise = this.exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Cannot find targeted exercise"));
    
        if (user.getRole() != UserRole.DOCTOR) {
            throw new Error("You are not authorized to update this exercise");
        }
    
        if (request.getDescription() != null) {
            foundExercise.setDescription(request.getDescription());
        }
        if (request.getTitle() != null) {
            foundExercise.setTitle(request.getTitle());
        }
    
        if (request.getExerciseItemInfo() != null && request.getExerciseItemInfo().length > 0) {
            List<ExerciseItem> existingItems = exerciseItemRepository.findByExercise(foundExercise);
    
            Map<Long, ExerciseItem> existingItemMap = existingItems.stream()
                    .collect(Collectors.toMap(ExerciseItem::getItem_id, Function.identity()));
    
            for (UpdateExerciseRequestDto.ExerciseItemInfo itemInfo : request.getExerciseItemInfo()) {
                if (itemInfo.getItem_id() != null && existingItemMap.containsKey(itemInfo.getItem_id())) {
                    ExerciseItem existingItem = existingItemMap.get(itemInfo.getItem_id());
                    if (itemInfo.getSource_url() != null) {
                        existingItem.setSource_url(itemInfo.getSource_url());
                    }
                    if (itemInfo.getDuration() != null) {
                        existingItem.setDuration(itemInfo.getDuration());
                    }
                    if (itemInfo.getContent() != null) {
                        existingItem.setContent(itemInfo.getContent());
                    }
                    exerciseItemRepository.save(existingItem); // Save the updated item
                } else if (itemInfo.getItem_id() == null) {
                    ExerciseItem newItem = new ExerciseItem();
                    newItem.setExercise_id(foundExercise.getExercise_id());
                    newItem.setSource_url(itemInfo.getSource_url());
                    newItem.setDuration(itemInfo.getDuration());
                    newItem.setContent(itemInfo.getContent());
                    newItem.setCreated_at(Instant.now()); // Assuming BaseModel handles this or you set it
                    exerciseItemRepository.save(newItem); // Save the new item
                } else {
                    System.out.println("Warning: ExerciseItem with ID " + itemInfo.getItem_id() + " not found.");
                }
            }
        }
    
        Exercise updatedExercise = this.exerciseRepository.save(foundExercise);
        return convertDto(updatedExercise);
    }

    private ExerciseResponseDto convertDto(Exercise exercise) {
        List<Comment> comments = commentRepository.findByExercise(exercise);
        List<ExerciseItem> exerciseItems = exerciseItemRepository.findByExercise(exercise);
        System.out.println(exercise.getPatient_id());
        System.out.println(exercise.getDoctor_id());
        User patient = userRepository.findById(exercise.getPatient_id()).orElseThrow(() -> new UserNotFoundException());
        User doctor = userRepository.findById(exercise.getDoctor_id()).orElseThrow(() -> new UserNotFoundException());
        Patient detailPatient = new Patient();
        Doctor detailDoctor = doctorRepository.findByUser(doctor);
        ExerciseResponseDto.PatientInfo patientInfo = new ExerciseResponseDto.PatientInfo(
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
        ExerciseResponseDto.DoctorInfo doctorInfo = new ExerciseResponseDto.DoctorInfo(
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
        return new ExerciseResponseDto(
            exercise.getExercise_id(),
            exercise.getDescription(),
            exercise.getTitle(),
            exercise.getCreated_at(),
            exercise.getUpdated_at(),
            exerciseItems,
            comments,
            patientInfo,
            doctorInfo
        );
    }

}
