package com.grupoK.consumer.modelDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.grupoK.connector.database.entities.enums.TipoCategoria;

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
public class CategoriaDto {

	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("descripcion")
    private TipoCategoria descripcion;
}
