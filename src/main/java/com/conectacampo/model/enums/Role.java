package com.conectacampo.model.enums;

public enum Role {
    FARMER("Agricultor"),
    BUYER("Comprador"),
    ADMIN("Administrador");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}