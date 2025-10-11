package com.grupoK.consumer.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DonacionDto {

    private String categoria;

    private String descripcion;

    private Integer cantidad;
}
