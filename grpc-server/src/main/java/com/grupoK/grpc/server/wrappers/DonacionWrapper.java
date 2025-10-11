package com.grupoK.grpc.server.wrappers;

import com.grupoK.connector.database.entities.Donacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DonacionWrapper {
	
	@Autowired
	private CategoriaWrapper categoriaWrapper;
	
	@Autowired
	private UsuarioWrapper usuarioWrapper;

	public com.grupoK.grpc.proto.Donacion toGrpcDonacion(Donacion donacionModel){
		return com.grupoK.grpc.proto.Donacion.newBuilder()
				.setId(donacionModel.getId())
				.setCategoria(categoriaWrapper.toGrpcCategoria(donacionModel.getCategoria()))
				.setDescripcion(donacionModel.getDescripcion())
				.setCantidad(donacionModel.getCantidad())
				.setEliminado(donacionModel.getEliminado())
				.build();
	}
	
	public Donacion toEntityDonacion(com.grupoK.grpc.proto.Donacion donacionModel) {
		return new Donacion(donacionModel.getId(), null, categoriaWrapper.toEntityCategoria(donacionModel.getCategoria()),
				donacionModel.getDescripcion(), donacionModel.getCantidad(),
				donacionModel.getEliminado(), null, null, null, usuarioWrapper.toEntityUsuario(donacionModel.getUsuario()), null, null);
	}	
}
