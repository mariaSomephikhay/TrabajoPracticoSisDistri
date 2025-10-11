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

    @JsonProperty("id_solicitud")
    private Integer idSolicitud;

    @JsonProperty("id_organizacion_solicitante")
    private Integer idOrganizacionSolicitante;

    @JsonProperty("id_organizacion_donante")
    private Integer idOrganizacionDonante;

    private List<DonacionDto> donaciones;

}

