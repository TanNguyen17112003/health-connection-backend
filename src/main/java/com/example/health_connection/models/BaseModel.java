package com.example.health_connection.models;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseModel {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant created_at;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant updated_at;
}
