package com.example.health_connection.enums;

public enum UserRole {
    PATIENT("PATIENT"),
    DOCTOR("DOCTOR");

    private final String UserRole;
    
    UserRole(String role) {
        this.UserRole = role;
    }

    public String getUserRole() {
        return this.UserRole;
    }

}
