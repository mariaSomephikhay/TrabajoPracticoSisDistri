package com.grupoK.Tp1SistemasDistribuidos.wrappers;


import org.springframework.stereotype.Component;

import com.grupoK.Tp1SistemasDistribuidos.entities.Categoria;
import com.grupoK.Tp1SistemasDistribuidos.enums.TipoCategoria;

@Component
public class CategoriaWrapper {
	
	
	public com.grupoK.grpc.Categoria toGrpcCategoria(Categoria categoriaModel){
		return com.grupoK.grpc.Categoria.newBuilder()
				.setId(categoriaModel.getId())
				.setDescripcion(categoriaModel.getDescripcion().getDescription())
				.build();
	}
	
	public Categoria toEntityCategoria(com.grupoK.grpc.Categoria categoriaModel) {
		return new Categoria(categoriaModel.getId(), TipoCategoria.valueOf(categoriaModel.getDescripcion()));
	}

}
