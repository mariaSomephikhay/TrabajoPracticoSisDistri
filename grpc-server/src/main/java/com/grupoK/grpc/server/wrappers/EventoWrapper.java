package com.grupoK.grpc.server.wrappers;

import com.grupoK.connector.database.entities.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utils.Utilidades;


@Component
public class EventoWrapper {

	@Autowired
	private UsuarioWrapper usuarioWrapper;
	
	Utilidades utilidades = new Utilidades();
	
	public com.grupoK.grpc.proto.Evento toGrpcEvento(Evento eventoModel) {
        return com.grupoK.grpc.proto.Evento.newBuilder()
                .setId(eventoModel.getId())
                .setNombre(eventoModel.getNombre())
                .setDescripcion(eventoModel.getDescripcion())
                .setFecha(utilidades.fromLocalDateTime(eventoModel.getFecha()))
                .setUsuario(usuarioWrapper.toGrpcUsuario(eventoModel.getIdUsuarioAlta()))
                .build();
    }
    
    public Evento toEntityEvento(com.grupoK.grpc.proto.Evento eventoModel) {
    	return new Evento (eventoModel.getId(),
    			eventoModel.getNombre(),
    			eventoModel.getDescripcion(),
    			utilidades.toLocalDateTime(eventoModel.getFecha()),
    			null,null,
    			usuarioWrapper.toEntityUsuario(eventoModel.getUsuario()));
    }
}
