package com.example.health_connection.controllers;

import com.example.health_connection.dtos.MultipleFileUploadResponseDto;
import com.example.health_connection.dtos.SingleFileUploadResponseDto;
import com.example.health_connection.services.CloudinaryService;
import com.example.health_connection.utils.Constant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.CLOUDINARY_CONTROLLER_PATH)
@Tag(name = "Cloudinary upload", description = "APIS upload files into cloudinary (image and video)")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping(value="/multiple/images", consumes="multipart/form-data")
    @Operation(summary = "Upload multiple images into cloudinary")
    public ResponseEntity<MultipleFileUploadResponseDto> uploadMultipleImages(@RequestParam("files") List<MultipartFile> files)  {
        MultipleFileUploadResponseDto response = cloudinaryService.uploadFiles(files, "image");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/single/image", consumes="multipart/form-data")
    @Operation(summary = "Upload single image into cloudinary")
    public ResponseEntity<SingleFileUploadResponseDto> uploadSingleImage(@RequestParam("file") MultipartFile file)  {
        SingleFileUploadResponseDto response = cloudinaryService.uploadFile(file, "image");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/multiple/videos", consumes="multipart/form-data")
    @Operation(summary = "Upload multiple videos into cloudinary")
    public ResponseEntity<MultipleFileUploadResponseDto> uploadMultipleVideos(@RequestParam("files") List<MultipartFile> files) {
        MultipleFileUploadResponseDto response = cloudinaryService.uploadFiles(files, "video");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/single/video", consumes="multipart/form-data")
    @Operation(summary = "Upload single video into cloudinary")
    public ResponseEntity<SingleFileUploadResponseDto> uploadSingleVideo(@RequestParam("file") MultipartFile file)  {
        SingleFileUploadResponseDto response = cloudinaryService.uploadFile(file, "video");
        return ResponseEntity.ok(response);
    }
}