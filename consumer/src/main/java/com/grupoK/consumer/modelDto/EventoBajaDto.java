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
public class EventoBajaDto {
	@JsonProperty("id_evento")
    private String id;
	
	@JsonProperty("id_organizacion")
    private String idOrganizacion;

}
