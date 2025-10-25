package com.grupoK.connector.database.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@ToString
@Table(name = "donacion")
public class Donacion {
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion",nullable=false)
    private Organizacion organizacion;*/

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_categoria",nullable=false)
	private Categoria categoria;
	
	@Column(name="descripcion", nullable=false, length=50)
	private String descripcion;
	
	@Column(name="cantidad", nullable=false)
	private int cantidad;
	
	@Column(name="eliminado", nullable=false)
	private Boolean eliminado;
	
	@CreationTimestamp
	@Column(name="fecha_alta", nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaAlta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_usuario_alta",nullable=false)
	private Usuario usuarioAlta;
	
	@UpdateTimestamp
	@Column(name="fecha_modificacion", nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaModificacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_usuario_modificacion",nullable=false)
	private Usuario usuarioModificacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_oferta",nullable=true)
    private Oferta oferta;

}
