package com.grupoK.web_service_server.rest.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.entities.enums.TipoCategoria;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class InformeSolicitudDetalleDto {
	 private String categoria;
	 private LocalDateTime FechaAlta;
	 private String descripcion;
	 private int cantidad;
	 private Boolean eliminado;
	 private Usuario usuarioAlta;
	 private Usuario usuarioModificacion;
}
