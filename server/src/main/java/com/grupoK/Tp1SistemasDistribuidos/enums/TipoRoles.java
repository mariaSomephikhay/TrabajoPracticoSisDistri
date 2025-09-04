package com.grupoK.Tp1SistemasDistribuidos.enums;

public enum TipoRoles {
	
    PRESIDENTE("Presidente"),
    VOCAL("Vocal"),
    COORDINADOR("Coordinador"),
    VOLUNTARIO("Voluntario");

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
