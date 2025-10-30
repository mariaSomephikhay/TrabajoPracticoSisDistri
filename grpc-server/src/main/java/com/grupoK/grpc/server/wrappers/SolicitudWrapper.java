package com.grupoK.grpc.server.wrappers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.grpc.proto.DonacionList;

@Component
public class SolicitudWrapper {
	
	@Autowired
	private OrganizacionWrapper organizacionWrapper;
	
	@Autowired
	private DonacionWrapper donacionWrapper;
	
    public com.grupoK.grpc.proto.SolicitudDonacion toGrpcSolicitudDonacion(Solicitud solicitud, List<Donacion> donaciones) {
        
    	return com.grupoK.grpc.proto.SolicitudDonacion.newBuilder()
        		.setId(solicitud.getId())
        		.setOrganizacionSolicitante(organizacionWrapper.toGrpcRol(solicitud.getOrganizacionSolicitante()))
        		.setActiva(solicitud.getActiva())
                .setProcesada(solicitud.getProcesada())
        		.addAllDonaciones(donaciones.stream()
                        .map(donacionWrapper::toGrpcDonacion)
                        .toList()
                )
                .build();
    }
    

}
