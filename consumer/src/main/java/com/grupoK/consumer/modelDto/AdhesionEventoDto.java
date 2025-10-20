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
public class AdhesionEventoDto {
	
	@JsonProperty("id_evento")
	private String idEvento;
	
	@JsonProperty("voluntario")
	private VoluntarioDto voluntario;
}
