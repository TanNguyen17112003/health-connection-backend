package com.example.health_connection.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import org.springframework.http.MediaType;
import com.cloudinary.utils.ObjectUtils;
import com.example.health_connection.dtos.MultipleFileUploadResponseDto;
import com.example.health_connection.dtos.SingleFileUploadResponseDto;
import com.example.health_connection.exceptions.FileReadException;
import com.example.health_connection.exceptions.BadFileRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {
    private final Cloudinary cloudinary;
    private final List<String> allowedImageTypes = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE
    );
    private final List<String> allowedVideoTypes = Arrays.asList(
            "video/mp4",
            "video/mpeg"
    );
    public MultipleFileUploadResponseDto uploadFiles(List<MultipartFile> files, String resourceType) {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            try {
                if (resourceType.equalsIgnoreCase("image") && !allowedImageTypes.contains(contentType)) {
                    throw new BadFileRequestException();
                }
    
                if (resourceType.equalsIgnoreCase("video") && !allowedVideoTypes.contains(contentType)) {
                    throw new BadFileRequestException();
                }
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", resourceType));
                String fileUrl = (String) uploadResult.get("url");
                fileUrls.add(fileUrl);
            } catch (IOException e) {
                log.error("Error reading file: {}", file.getOriginalFilename(), e);
                throw new FileReadException("Could not read file: " + file.getOriginalFilename(), e);
            } 
        }
        return new MultipleFileUploadResponseDto(fileUrls);
    }

    public SingleFileUploadResponseDto uploadFile(MultipartFile file, String resourceType) {
        String contentType = file.getContentType();
        try {
            if (resourceType.equalsIgnoreCase("image") && !allowedImageTypes.contains(contentType)) {
                throw new BadFileRequestException();
            }
            if (resourceType.equalsIgnoreCase("video") && !allowedVideoTypes.contains(contentType)) {
                throw new BadFileRequestException();
            }
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", resourceType));
            String fileUrl = (String) uploadResult.get("url");
            return new SingleFileUploadResponseDto(fileUrl);
        } catch (IOException e) {
            log.error("Error reading file: {}", file.getOriginalFilename(), e);
            throw new FileReadException("Could not read file: " + file.getOriginalFilename(), e);
        } catch (MaxUploadSizeExceededException e) {
            return new SingleFileUploadResponseDto("Wait too long");
        }
    }

    public MultipleFileUploadResponseDto uploadFiles(List<MultipartFile> files) {
        return uploadFiles(files, "auto");
    }

    public SingleFileUploadResponseDto uploadFile(MultipartFile file) {
        return uploadFile(file, "auto");
    }
}