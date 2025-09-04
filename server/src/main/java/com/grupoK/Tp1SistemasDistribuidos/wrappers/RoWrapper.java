package com.grupoK.Tp1SistemasDistribuidos.wrappers;

import org.springframework.stereotype.Component;
import com.grupoK.Tp1SistemasDistribuidos.entities.Rol;
import com.grupoK.Tp1SistemasDistribuidos.enums.TipoRoles;

@Component
public class RoWrapper {
    public com.grupoK.grpc.Rol toGrpcRol(Rol rolModel) {
        return com.grupoK.grpc.Rol.newBuilder()
                .setId(rolModel.getId())
                .setDescripcion(rolModel.getRol().getDescription())
                .build();
    }
    
    public Rol toEntityRol(com.grupoK.grpc.Rol rolModel) {
    	return new Rol(
    			rolModel.getId(), 
    			TipoRoles.valueOf(rolModel.getDescripcion()));
    }
}
