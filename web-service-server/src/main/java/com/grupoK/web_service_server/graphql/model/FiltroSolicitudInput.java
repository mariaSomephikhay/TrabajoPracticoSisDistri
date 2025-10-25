package com.grupoK.web_service_server.graphql.model;

import com.grupoK.connector.database.entities.Categoria;
import com.grupoK.connector.database.entities.enums.TipoCategoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroSolicitudInput {
    private TipoCategoria categoria;
    private String fechaDesde;
    private String fechaHasta;
    private String eliminado;

}
