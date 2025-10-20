package com.grupoK.connector.database.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "voluntario")
public class Voluntario {
	@NonNull
	@Id
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion",nullable=false)
    private Organizacion organizacion;
	
	@Column(name="email", unique = true, nullable=false, length=50)
	private String email;
	
	@Column(name="nombre", nullable=false, length=25)
	private String nombre;
	
	@Column(name="apellido", nullable=false, length=25)
	private String apellido;
	
	@Column(name="telefono", nullable=true, length=10)
	private String telefono;
	
	
    
}
