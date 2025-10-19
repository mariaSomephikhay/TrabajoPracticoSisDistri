package com.grupoK.connector.database.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(
	    name = "evento_donacion",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"id_evento", "id_donacion"})
	    }
	)
public class EventoDonacion {
	
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_evento",nullable=false)
	private Evento evento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_donacion",nullable=false)
	private Donacion donacion;
	
	@Column(name="cantidad_repartida", nullable=false, length=30)
	private int cantRepartida;
	
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
	private Usuario usuarioModificacion;
}
