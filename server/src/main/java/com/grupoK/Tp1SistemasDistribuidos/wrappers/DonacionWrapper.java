package com.grupoK.Tp1SistemasDistribuidos.wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;
@Component
public class DonacionWrapper {
	
	@Autowired
	private CategoriaWrapper categoriaWrapper;
	
	@Autowired
	private UsuarioWrapper usuarioWrapper;
	
	public com.grupoK.grpc.Donacion toGrpcDonacion(Donacion donacionModel){
		return com.grupoK.grpc.Donacion.newBuilder()
				.setId(donacionModel.getId())
				.setCategoria(categoriaWrapper.toGrpcCategoria(donacionModel.getCategoria()))
				.setDescripcion(donacionModel.getDescripcion())
				.setCantidad(donacionModel.getCantidad())
				.setEliminado(donacionModel.getEliminado())
				.build();
	}
	
	public Donacion toEntityDonacion(com.grupoK.grpc.Donacion donacionModel) {
		return new Donacion(donacionModel.getId(), categoriaWrapper.toEntityCategoria(donacionModel.getCategoria()),
				donacionModel.getDescripcion(), donacionModel.getCantidad(),
				donacionModel.getEliminado(), null, null, null, usuarioWrapper.toEntityUsuario(donacionModel.getUsuario()));
	}	
}
