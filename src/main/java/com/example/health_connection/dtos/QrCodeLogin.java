package com.example.health_connection.dtos;

import lombok.Data;
import java.io.Serializable;


@Data
public class QrCodeLogin implements Serializable {
    private String uuid;
    private Long userId; 
    private boolean scanned;
    private boolean confirmed;
}