package com.conectacampo.model.enums;

public enum FairStatus {
    UPCOMING("Próxima"),
    ACTIVE("Activa hoy"),
    FINISHED("Finalizada"),
    CANCELLED("Cancelada");

    private final String displayName;

    FairStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}