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
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="evento")
public class Evento {
	
//	@NonNull
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
	
	@NonNull
    @Id
    private String id;
	
	@Column(name="nombre", nullable=false, length=30)
	private String nombre;
	
	@Column(name="descripcion", nullable=false, length=250)
	private String descripcion;
	
	@Column(name="fecha", nullable=false)
	private LocalDateTime fecha;
	
	@ManyToMany
    @JoinTable(
        name = "evento_usuario",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios = new ArrayList<>();
	
	@CreationTimestamp
	@Column(name="fecha_alta", nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaAlta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_usuario_alta",nullable=false)
	private Usuario idUsuarioAlta;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion",nullable=false)
    private Organizacion organizacion;
	
	@Column(name="activo", nullable=false)
	private Boolean activo;
	
	@Column(name="publicado", nullable=false)
	private Boolean publicado;
	
	@ManyToMany
    @JoinTable(
        name = "evento_voluntario",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "voluntario_id")
    )
    private List<Voluntario> voluntarios = new ArrayList<>();
}
