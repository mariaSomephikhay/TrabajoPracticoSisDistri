package com.grupoK.consumer.modelDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudBajaDto {
	@JsonProperty("id_solicitud_donacion")
    private String id;
	
	@JsonProperty("id_organizacion_solicitante")
    private String idOrganizacion;

}
