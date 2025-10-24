package com.grupoK.grpc.server.wrappers;

import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.service.IOrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utils.Utilidades;


@Component
public class EventoWrapper {

	@Autowired
	private UsuarioWrapper usuarioWrapper;
	
	@Autowired
	private IOrganizacionService organizacionService;
	
	Utilidades utilidades = new Utilidades();
	
	public com.grupoK.grpc.proto.Evento toGrpcEvento(Evento eventoModel) {
		
		com.grupoK.grpc.proto.Evento evento = com.grupoK.grpc.proto.Evento.newBuilder()
		.setId(eventoModel.getId())
        .setNombre(eventoModel.getNombre())
        .setDescripcion(eventoModel.getDescripcion())
        .setFecha(utilidades.fromLocalDateTime(eventoModel.getFecha()))
        .setUsuario(usuarioWrapper.toGrpcUsuario(eventoModel.getIdUsuarioAlta()))
        .setIdOrganizacion(eventoModel.getOrganizacion().getId())
        .setActivo(eventoModel.getActivo())
        .setPublicado(eventoModel.getPublicado())
        .build();
		
        return evento;
    }
    
    public Evento toEntityEvento(com.grupoK.grpc.proto.Evento eventoModel) {
    	
    	Organizacion organizacion = null;
    	
		try {
			organizacion = organizacionService.findById(eventoModel.getIdOrganizacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Evento (eventoModel.getId(),
    			eventoModel.getNombre(),
    			eventoModel.getDescripcion(),
    			utilidades.toLocalDateTime(eventoModel.getFecha()),
    			null,null,
    			usuarioWrapper.toEntityUsuario(eventoModel.getUsuario()),
    			organizacion, eventoModel.getActivo(), eventoModel.getPublicado(),null,null);
    }
}
