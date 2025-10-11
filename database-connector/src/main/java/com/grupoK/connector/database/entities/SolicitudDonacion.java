package com.grupoK.connector.database.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "solicitud_donacion",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_solicitud", "id_donacion"})
        }
)
public class SolicitudDonacion {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_solicitud",nullable=false)
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_donacion",nullable=false)
    private Donacion donacion;

    @Column(name="cantidad_solicitada", nullable=false, length=30)
    private Integer cantidadSolicitada;

}
