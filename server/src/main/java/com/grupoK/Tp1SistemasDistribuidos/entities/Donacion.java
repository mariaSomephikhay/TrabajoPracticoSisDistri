package com.grupoK.Tp1SistemasDistribuidos.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "donacion")
public class Donacion {
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_categoria",nullable=false)
	private Categoria categoria;
	
	@Column(name="descripcion", nullable=false, length=25)
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
	private Usuario idUsuarioAlta;
	
	@UpdateTimestamp
	@Column(name="fecha_modificacion", nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaModificacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_usuario_modificacion",nullable=false)
	private Usuario idUsuarioModificacion;
	

}
