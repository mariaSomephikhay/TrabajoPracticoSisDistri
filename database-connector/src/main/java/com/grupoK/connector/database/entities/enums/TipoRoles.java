package com.grupoK.connector.database.entities.enums;

public enum TipoRoles {
	
    PRESIDENTE("PRESIDENTE"),
    VOCAL("VOCAL"),
    COORDINADOR("COORDINADOR"),
    VOLUNTARIO("VOLUNTARIO");

    private final String description;

    TipoRoles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
