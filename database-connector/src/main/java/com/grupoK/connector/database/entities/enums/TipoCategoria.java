package com.grupoK.connector.database.entities.enums;

public enum TipoCategoria {
    ALIMENTO("ALIMENTO"),
    JUGUETE("JUGUETE"),
    ROPA("ROPA"),
    UTIL_ESCOLAR("UTIL_ESCOLAR");

    private final String description;

    TipoCategoria(String description) {
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
