package com.grupoK.grpc.server.wrappers;


import org.springframework.stereotype.Component;
import com.grupoK.connector.database.entities.Organizacion;


@Component
public class OrganizacionWrapper {
	
    public com.grupoK.grpc.proto.Organizacion toGrpcRol(Organizacion organizacionModel) {
        return com.grupoK.grpc.proto.Organizacion.newBuilder()
                .setId(organizacionModel.getId())
                .setNombre(organizacionModel.getNombre())
                .setExterna(organizacionModel.getExterna())
                .build();
    }
    
    public Organizacion toEntityOrganizacion(com.grupoK.grpc.proto.Organizacion organizacionModel) {
    	return new Organizacion(
    			organizacionModel.getId(),
    			organizacionModel.getNombre(),
    			organizacionModel.getExterna()
    			);
    }

}
