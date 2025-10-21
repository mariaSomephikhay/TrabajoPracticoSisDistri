package com.grupoK.grpc.server.wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.entities.Voluntario;

@Component
public class VoluntarioWrapper {
	
	@Autowired
	private OrganizacionWrapper organizacionWrapper;

	public com.grupoK.grpc.proto.Voluntario toGrpcVoluntario(Voluntario voluntario) {
		Organizacion organizacion = voluntario.getOrganizacion();
        return com.grupoK.grpc.proto.Voluntario.newBuilder()
                .setIdVoluntario(voluntario.getId())
                .setApellido(voluntario.getApellido())
                .setNombre(voluntario.getNombre())
                .setEmail(voluntario.getEmail())
                .setTelefono(StringUtils.isEmpty(voluntario.getTelefono()) ? "" : voluntario.getTelefono())
                .setOrganizacion(organizacionWrapper.toGrpcRol(organizacion))
                .build();
    }
}
