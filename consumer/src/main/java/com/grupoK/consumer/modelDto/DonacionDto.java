package com.grupoK.consumer.modelDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DonacionDto {
	
	@JsonProperty("categoria")
    private CategoriaDto categoria;
	
	@JsonProperty("descripcion")
    private String descripcion;
	
	@JsonProperty("cantidad")
    private Integer cantidad;
}
