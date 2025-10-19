package com.grupoK.consumer.modelDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudDto {

    @JsonProperty("id_solicitud_donacion")
    private String id;

    @JsonProperty("id_organizacion_solicitante")
    private Integer idOrganizacionSolicitante;

    @JsonProperty("donacion")
    private List<DonacionDto> donaciones;

}

