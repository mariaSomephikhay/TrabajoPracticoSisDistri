package com.grupoK.connector.database.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oferta")
public class Oferta {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion_donante",nullable=false)
    private Organizacion organizacionDonante;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "oferta")
    private List<Donacion> donaciones;

    @CreationTimestamp
    @Column(name="fecha_alta", nullable=true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fechaAlta;

}
