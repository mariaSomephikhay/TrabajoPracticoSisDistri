package com.grupoK.web_service_server.graphql.model;

import com.grupoK.connector.database.entities.enums.TipoCategoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InformeSolicitudDto {
	private TipoCategoria categoria;
	private String eliminado;
	private int cantidad;
	private Boolean recibida;
	
}
