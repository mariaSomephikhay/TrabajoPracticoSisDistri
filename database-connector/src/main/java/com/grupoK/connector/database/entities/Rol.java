package com.grupoK.connector.database.entities;

import com.grupoK.connector.database.entities.enums.TipoRoles;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="rol_usuario")
public class Rol {
	
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @Column(name="rol", nullable = false )
    @Enumerated(EnumType.STRING) 
    private TipoRoles rol;
    
}
