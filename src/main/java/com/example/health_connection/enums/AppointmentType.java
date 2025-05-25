package com.example.health_connection.enums;

public enum AppointmentType {
    FIRST_VISIT("FIRST_VISIT"),
    FOLLOW_UP("FOLLOW_UP");

    private final String AppointmentType;
    
    AppointmentType(String type) {
        this.AppointmentType = type;
    }

    public String getAppointmentType() {
        return this.AppointmentType;
    }

}
