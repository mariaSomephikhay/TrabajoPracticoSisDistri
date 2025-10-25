package com.grupoK.web_service_server.graphql.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.grupoK.connector.database.entities.Categoria;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.entities.enums.TipoCategoria;
import com.grupoK.connector.database.repositories.ICategoriaRepository;
import com.grupoK.connector.database.serviceImp.SolicitudService;
import com.grupoK.web_service_server.graphql.model.SolicitudDonacionGraphqlDto;


import com.grupoK.web_service_server.graphql.model.FiltroSolicitudInput;
import com.grupoK.web_service_server.graphql.model.GraphQLResponse;
import com.grupoK.web_service_server.graphql.model.InformeEventoDto;
import com.grupoK.web_service_server.graphql.model.InformeSolicitudDto;

@Controller
public class SolicitudGraphqlController {
	
	@Autowired
	SolicitudService solicitudService;	
	
	@Autowired
	ICategoriaRepository categoriaRepository;	
	
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
	
	@QueryMapping
	public List<InformeSolicitudDto> informeSolicitudes(@Argument FiltroSolicitudInput filtro) {
		LocalDateTime fechaDesdeDate = filtro.getFechaHasta() != null ? LocalDate.parse(filtro.getFechaHasta()).atStartOfDay()  : null;
		LocalDateTime fechaHastaDate = filtro.getFechaHasta() != null ? LocalDate.parse(filtro.getFechaHasta()).atStartOfDay() : null;
	    Boolean eliminado;
	    if (filtro.getEliminado()==null || filtro.getEliminado().equals("")) {
	    	eliminado = null;
	    } else if (filtro.getEliminado().equalsIgnoreCase("si")) {
	    	eliminado = true;
	    }else {
	    	eliminado = false;
	    }
	   /* Categoria c;
	    System.out.println(filtro.getCategoria().getDescription());
	    if (filtro.getCategoria()!=null && !filtro.getCategoria().getDescription().equals("")) {
	    	System.out.println(filtro.getCategoria().getDescription());
	    	c = categoriaRepository.findByDescripcion(filtro.getCategoria().getDescription()).get();
	    	System.out.println("a");
	    }else {
	    	c = null;
	    }*/
	    System.out.println(filtro.getCategoria());
	    List<Solicitud> solicitudes = solicitudService.find(filtro.getCategoria(), fechaDesdeDate, fechaHastaDate, eliminado);
	    
	    
		List<InformeSolicitudDto> informes = new ArrayList<>();; 
		for (Solicitud solicitud : solicitudes) {
			try {
			List<SolicitudDonacion> donationsAssociated = solicitudService.findAllDonationsAssociatedByRequest(solicitud);
			List<Donacion> donaciones = donationsAssociated.stream()
	                .map(SolicitudDonacion::getDonacion)
	                .toList();
			String eliminadoINF = solicitud.getActiva() ? "NO" : "SI";
			Boolean recibida = solicitud.getOrganizacionSolicitante().getId()!=1;
			for (Donacion d : donaciones) {
			System.out.println(solicitud.getId());
				TipoCategoria categoria = d.getCategoria().getDescripcion();
				
				Optional<InformeSolicitudDto> encontrada = informes.stream()
					.filter(inf -> categoria.equals(inf.getCategoria()))
					.filter(inf -> eliminadoINF.equals(inf.getEliminado()))
					.filter(inf -> eliminadoINF.equals(inf.getEliminado()))
					.filter(inf -> recibida==inf.getRecibida())
				    .findFirst();

				if (encontrada.isPresent()) {
					encontrada.get().setCantidad(encontrada.get().getCantidad() + d.getCantidad() );
				}else {
					InformeSolicitudDto informeNuevo = new InformeSolicitudDto(categoria, eliminadoINF, d.getCantidad(), recibida);
					informes.add(informeNuevo);
				}
			}
			}catch (Exception e) {
				System.out.println("Solicitud sin donaciones: " + e);
			}
			
		}
		System.out.println(solicitudes);
		System.out.println(informes);
		return informes;
	}
	

    
    
}


