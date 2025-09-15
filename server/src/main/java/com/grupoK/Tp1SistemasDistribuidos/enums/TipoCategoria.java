package com.grupoK.Tp1SistemasDistribuidos.enums;

public enum TipoCategoria {
    ALIMENTO("ALIMENTO"),
    JUGUETE("JUEGUETE"),
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
