package com.example.health_connection.enums;

public enum AppointmentStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    RESCHEDULED("RESCHEDULED"),
    REJECTED("REJECTED"),
    ACCEPTED("ACCEPTED");

    private final String AppointmentStatus;
    
    AppointmentStatus(String status) {
        this.AppointmentStatus = status;
    }

    public String getAppointmentStatus() {
        return this.AppointmentStatus;
    }

}
