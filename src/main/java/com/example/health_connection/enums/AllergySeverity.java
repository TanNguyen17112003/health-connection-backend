package com.example.health_connection.enums;

public enum AllergySeverity {
    DANGEROUS("DANGEROUS"),
    SAFE("SAFE");

    private final String Allergy;
    
    AllergySeverity(String role) {
        this.Allergy = role;
    }

    public String getAllergy() {
        return this.Allergy;
    }

}
