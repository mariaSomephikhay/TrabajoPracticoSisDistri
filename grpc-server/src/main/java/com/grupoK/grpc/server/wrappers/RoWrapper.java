package com.grupoK.grpc.server.wrappers;

import com.grupoK.connector.database.entities.Rol;
import com.grupoK.connector.database.entities.enums.TipoRoles;

import org.springframework.stereotype.Component;

@Component
public class RoWrapper {
    public com.grupoK.grpc.proto.Rol toGrpcRol(Rol rolModel) {
        return com.grupoK.grpc.proto.Rol.newBuilder()
                .setId(rolModel.getId())
                .setDescripcion(rolModel.getRol().getDescription())
                .build();
    }
    
    public Rol toEntityRol(com.grupoK.grpc.proto.Rol rolModel) {
    	return new Rol(
    			rolModel.getId(), 
    			TipoRoles.valueOf(rolModel.getDescripcion()));
    }
}
