package com.grupoK.connector.database.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "solicitud")
public class Solicitud {

    @NonNull
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion_solicitante",nullable=false)
    private Organizacion organizacionSolicitante;

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name="id_organizacion_donante",nullable=false)
    //private Organizacion organizacionDonante;

    @Column(name="activa", nullable=false)
    private Boolean activa;

    @Column(name="procesada", nullable=false)
    private Boolean procesada;

    @CreationTimestamp
    @Column(name="fecha_alta", nullable=true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fechaAlta;

}
