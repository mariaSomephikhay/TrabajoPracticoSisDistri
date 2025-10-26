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
    private Integer idOferta;

    @JsonProperty("id_organizacion_donante")
    private Integer idOrganizacionDonante;

    private List<DonacionOfertaDto> donaciones;

    @Getter
    @Setter
    @ToString
    public static class DonacionOfertaDto {

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("categoria")
        private CategoriaDto categoria;

        @JsonProperty("descripcion")
        private String descripcion;

        @JsonProperty("cantidad")
        private Integer cantidad;
    }

}
