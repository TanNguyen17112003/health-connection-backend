package com.example.health_connection.controllers;
import com.example.health_connection.dtos.CreateCommentRequestDto;
import com.example.health_connection.dtos.CreateExerciseItemRequestDto;
import com.example.health_connection.dtos.CreateExerciseRequestDto;
import com.example.health_connection.dtos.DeleteResponseDto;
import com.example.health_connection.dtos.ExerciseResponseDto;
import com.example.health_connection.dtos.ReplyExerciseItemRequestDto;
import com.example.health_connection.dtos.UpdateExerciseRequestDto;
import com.example.health_connection.models.Comment;
import com.example.health_connection.models.ExerciseItem;
import com.example.health_connection.models.User;
import com.example.health_connection.services.ExerciseService;
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
@RequestMapping(Constant.EXERCISE_CONTROLLER_PATH)
@Tag(name = "Exercise", description="APIS for exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;
    @GetMapping("")
    @Operation(summary = "Get a list of exercise")
    public ResponseEntity<ExerciseResponseDto[]> getExercises(@AuthenticationPrincipal User user) {
        ExerciseResponseDto[] result = exerciseService.getExercises(user);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get detailed exercise based on Id attribute") 
    public ResponseEntity<ExerciseResponseDto> getExercise(@PathVariable Long id, @AuthenticationPrincipal User user) {
        ExerciseResponseDto result = exerciseService.getExercise(id, user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    @Operation(summary = "Create an exercise and assigned for one patient")
    public ResponseEntity<ExerciseResponseDto> createExercise(@RequestBody @Valid CreateExerciseRequestDto requestDto, @AuthenticationPrincipal User user) {
        ExerciseResponseDto result = this.exerciseService.createExercise(requestDto, user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/comment")
    @Operation(summary = "Doctor provides comments related to the exercise item patient has done")
    public ResponseEntity<Comment> provideComment(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody @Valid CreateCommentRequestDto request) {
        return ResponseEntity.ok(this.exerciseService.createCommentonExercise(id, user, request));
    }

    @PostMapping(value = "/item")
    @Operation(summary = "Create an exercise item after create the big exercise")
    public ResponseEntity<ExerciseItem> createExerciseItem(@RequestBody @Valid CreateExerciseItemRequestDto request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.exerciseService.createExerciseItem(request, user));
    }
    

    @PutMapping("/{id}")
    @Operation(summary = "Update description, title, exerciseItem or simply add a new exerciseitem")
    public ResponseEntity<ExerciseResponseDto> updateExercise(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody @Valid UpdateExerciseRequestDto requestDto) {
        ExerciseResponseDto result = this.exerciseService.updateExercise(id, user, requestDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/reply")
    @Operation(summary="Patient upload an exercise item to reply with a specified exercise item from doctor")
    public ResponseEntity<ExerciseItem> replyExercise(@PathVariable Long id, @RequestBody ReplyExerciseItemRequestDto request, @AuthenticationPrincipal User user) {
        ExerciseItem result = this.exerciseService.replyExerciseItem(id, user, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Doctor delete a specified exercise")
    public ResponseEntity<DeleteResponseDto> deleteExercise(@PathVariable Long id, @AuthenticationPrincipal User user) {
        DeleteResponseDto response = this.exerciseService.deleteExercise(id, user);
        return ResponseEntity.ok(response);
    }
    

}
