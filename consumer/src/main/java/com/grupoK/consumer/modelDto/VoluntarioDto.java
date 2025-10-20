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
public class VoluntarioDto {
	
	@JsonProperty("idOrganizacion")
	private String idOrganizacion;
	
	@JsonProperty("idVoluntario")
	private String idVoluntario;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("apellido")
	private String apellido;
	
	@JsonProperty("telefono")
	private String telefono;
	
	@JsonProperty("email")
	private String email;
}
