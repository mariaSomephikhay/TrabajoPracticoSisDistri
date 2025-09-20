package com.grupoK.Tp1SistemasDistribuidos.wrappers;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioWrapper {
	
	@Autowired
	private RoWrapper rolWrapper;
	
    public com.grupoK.grpc.Usuario toGrpcUsuario(Usuario userModel) {
        return com.grupoK.grpc.Usuario.newBuilder()
                .setId(userModel.getId())
                .setActivo(userModel.getActivo())
                .setApellido(userModel.getApellido())
                .setNombre(userModel.getNombre())
                .setUsername(userModel.getUsername())
                .setPassword(userModel.getPassword())
                .setEmail(userModel.getEmail())
                .setTelefono(StringUtils.isEmpty(userModel.getTelefono()) ? "" : userModel.getTelefono())
                .setRol(rolWrapper.toGrpcRol(userModel.getRol()))
                .build();
    }
    
    public Usuario toEntityUsuario(com.grupoK.grpc.Usuario userModel) {
    	System.out.println("rol" + userModel.getRol());
    	return new Usuario(
    			userModel.getId(), 
    			userModel.getUsername(), 
    			userModel.getPassword(),
    			userModel.getEmail(),
    			userModel.getNombre(), 
    			userModel.getApellido(), 
    			StringUtils.isEmpty(userModel.getTelefono()) ? null : userModel.getTelefono(), 
    			userModel.getActivo(), 
    			null, 
    			null,
    			null,
    			rolWrapper.toEntityRol(userModel.getRol()));
    }
    
}
