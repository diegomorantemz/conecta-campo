package com.conectacampo.model.enums;

public enum HarvestStatus {
    ACTIVE("Disponible"),
    IN_PROGRESS("En conversación"),
    COMPLETED("Vendido"),
    CANCELLED("Cancelado");

    private final String displayName;

    HarvestStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}