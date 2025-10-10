package com.grupoK.connector.database.entities;

import com.grupoK.connector.database.entities.enums.TipoCategoria;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="categoria_donacion")
public class Categoria {

	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @Column(name="descripcion", nullable = false )
    @Enumerated(EnumType.STRING) 
    private TipoCategoria descripcion;
}

