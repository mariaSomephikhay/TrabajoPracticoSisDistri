package com.grupoK.Tp1SistemasDistribuidos.grpcService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.serviceImp.UsuarioService;
import com.grupoK.Tp1SistemasDistribuidos.wrappers.UsuarioWrapper;
import com.grupoK.grpc.Empty;
import com.grupoK.grpc.ManagerServiceGrpc;
import com.grupoK.grpc.UsuarioList;

import io.grpc.stub.StreamObserver;

@GrpcService
public class ManagerServiceImpl extends ManagerServiceGrpc.ManagerServiceImplBase{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioWrapper usuarioWrapper;
	
    @Override
    public void getAllUsers(Empty request, StreamObserver<UsuarioList> responseObserver) {

        // request -> DB -> map response -> return
        List<Usuario> users = usuarioService.findAll();

        UsuarioList response = UsuarioList.newBuilder()
                .addAllUsuarios(users.stream()
                		.map(usuarioWrapper::toGrpcUsuario).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
	
	@Override
	public void insertOrUpdateUser(com.grupoK.grpc.Usuario request, StreamObserver<com.grupoK.grpc.Usuario> responseObserver) {
        // request -> DB -> map response -> return
		Usuario newUser = usuarioService.saveOrUpdate(usuarioWrapper.toEntityUsuario(request));
        responseObserver.onNext(usuarioWrapper.toGrpcUsuario(newUser));
        responseObserver.onCompleted();
	}

    
}
