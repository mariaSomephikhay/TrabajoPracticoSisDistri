package com.grupoK.grpc.server.wrappers;

import com.grupoK.connector.database.entities.Categoria;
import com.grupoK.connector.database.entities.enums.TipoCategoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaWrapper {
	
	
	public com.grupoK.grpc.proto.Categoria toGrpcCategoria(Categoria categoriaModel){
		return com.grupoK.grpc.proto.Categoria.newBuilder()
				.setId(categoriaModel.getId())
				.setDescripcion(categoriaModel.getDescripcion().getDescription())
				.build();
	}
	
	public Categoria toEntityCategoria(com.grupoK.grpc.proto.Categoria categoriaModel) {
		return new Categoria(categoriaModel.getId(), TipoCategoria.valueOf(categoriaModel.getDescripcion()));
	}

}
