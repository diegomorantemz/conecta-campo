package com.conectacampo.model.enums;

public enum ContactStatus {
    PENDING("Pendiente"),
    ACCEPTED("Aceptado"),
    REJECTED("Rechazado");

    private final String displayName;

    ContactStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}