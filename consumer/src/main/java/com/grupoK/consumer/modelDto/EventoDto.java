package com.grupoK.consumer.modelDto;

import java.time.LocalDateTime;
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
public class EventoDto {

	@JsonProperty("id_evento")
    private String id;
	
	@JsonProperty("id_organizacion")
    private String idOrganizacion;

    @JsonProperty("nombre")
    private String nombre;
    
    @JsonProperty("descripcion")
    private String descripcion;
    
    @JsonProperty("fecha")
    private LocalDateTime fecha;
}
