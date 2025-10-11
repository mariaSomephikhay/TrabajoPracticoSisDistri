package com.grupoK.consumer.modelDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OfertaDto {

    @JsonProperty("id_oferta")
    private Long idOferta;

    @JsonProperty("id_organizacion_donante")
    private Long idOrganizacionDonante;

    private List<DonacionDto> donaciones;

}
