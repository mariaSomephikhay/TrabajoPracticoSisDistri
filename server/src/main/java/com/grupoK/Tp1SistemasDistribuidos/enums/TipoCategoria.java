package com.grupoK.Tp1SistemasDistribuidos.enums;

public enum TipoCategoria {
    ROPA("ROPA"),
    ALIMENTO("ALIMENTO"),
    JUGUETE("JUEGUETE"),
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
