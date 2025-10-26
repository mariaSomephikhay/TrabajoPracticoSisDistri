package com.grupoK.consumer.modelDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudTransferDto {
    @JsonProperty("id_solicitud")
    private String id;

    @JsonProperty("id_organizacion_donante")
    private Integer idOrganizacionDonante;

    @JsonProperty("donaciones")
    private List<DonacionDto> donaciones;
}
