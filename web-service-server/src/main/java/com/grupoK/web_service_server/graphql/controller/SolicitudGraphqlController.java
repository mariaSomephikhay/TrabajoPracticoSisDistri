package com.grupoK.web_service_server.graphql.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.serviceImp.SolicitudService;
import com.grupoK.web_service_server.graphql.model.SolicitudDonacionGraphqlDto;
import com.grupoK.web_service_server.graphql.model.GraphQLResponse;

@Controller
public class SolicitudGraphqlController {
	
	@Autowired
	SolicitudService solicitudService;	
	
	@QueryMapping
	public SolicitudDonacionGraphqlDto getSolicitud(@Argument String id) {
		try {
			Solicitud solicitud = solicitudService.findById(id);
			List<SolicitudDonacion> donationsAssociated = solicitudService.findAllDonationsAssociatedByRequest(solicitud);
			List<Donacion> donaciones = donationsAssociated.stream()
	                .map(SolicitudDonacion::getDonacion)
	                .toList();
			SolicitudDonacionGraphqlDto soliDona = new SolicitudDonacionGraphqlDto(solicitud.getId(), solicitud.getOrganizacionSolicitante(),
					solicitud.getActiva(), solicitud.getProcesada(), donaciones);
			System.out.println(soliDona);
			return soliDona;
		}catch (Exception e){
			return null;
		}
	}
	
	/*
    @SchemaMapping
    public List<Donacion> donaciones(SolicitudDonacionGraphqlDto solicitudDon) throws Exception {
    	Solicitud solicitud = solicitudService.findById(solicitudDon.getId());
    	List<SolicitudDonacion> donationsAssociated = solicitudService.findAllDonationsAssociatedByRequest(solicitud);
    	List<Donacion> donaciones = donationsAssociated.stream()
                .map(SolicitudDonacion::getDonacion)
                .toList();
        return donaciones;
    }*/
}


