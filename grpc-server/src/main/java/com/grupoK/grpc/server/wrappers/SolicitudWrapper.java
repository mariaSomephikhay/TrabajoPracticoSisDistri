package com.grupoK.grpc.server.wrappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupoK.connector.database.entities.Solicitud;

@Component
public class SolicitudWrapper {
	
	@Autowired
	private OrganizacionWrapper organizacionWrapper;
	
    public com.grupoK.grpc.proto.SolicitudDonacion toGrpcSolicitudDonacion(Solicitud solicitud) {
        return com.grupoK.grpc.proto.SolicitudDonacion.newBuilder()
        		.setId(solicitud.getId())
        		.setOrganizacionSolicitante(organizacionWrapper.toGrpcRol(solicitud.getOrganizacionSolicitante()))
                .build();
    }
    

}
