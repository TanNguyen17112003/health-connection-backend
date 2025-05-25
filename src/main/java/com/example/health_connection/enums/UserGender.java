package com.example.health_connection.enums;

public enum UserGender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String userGender;
    
    UserGender(String gender) {
        this.userGender = gender;
    }

    public String getUserGender() {
        return this.userGender;
    }

}
