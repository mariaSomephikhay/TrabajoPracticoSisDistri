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
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

@Entity
@Table(name = "usuario")
public class Usuario {
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username", unique = true, nullable=false, length=15)
	private String username;
	
	@Column(name="password", nullable=false, length=60)
	private String password;
	
	@Column(name="email", unique = true, nullable=false, length=50)
	private String email;
	
	@Column(name="nombre", nullable=false, length=25)
	private String nombre;
	
	@Column(name="apellido", nullable=false, length=25)
	private String apellido;
	
	@Column(name="telefono", nullable=true, length=10)
	private String telefono;
	
	@Column(name="activo", nullable=false)
	private Boolean activo;
	
	@ManyToMany(mappedBy = "usuarios")
    private List<Evento> eventos = new ArrayList<>();
	
	@CreationTimestamp
	@Column(name="fecha_alta", nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaAlta;
	
	@UpdateTimestamp
	@Column(name="fecha_modificacion", nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaModificacion;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rol",nullable=false)
	private Rol rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion",nullable=false)
    private Organizacion organizacion;
	
}
