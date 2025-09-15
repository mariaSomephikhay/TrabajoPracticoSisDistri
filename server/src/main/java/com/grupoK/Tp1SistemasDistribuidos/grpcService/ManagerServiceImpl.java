package com.grupoK.Tp1SistemasDistribuidos.grpcService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.serviceImp.DonacionService;
import com.grupoK.Tp1SistemasDistribuidos.serviceImp.UsuarioService;
import com.grupoK.Tp1SistemasDistribuidos.wrappers.DonacionWrapper;
import com.grupoK.Tp1SistemasDistribuidos.wrappers.UsuarioWrapper;
import com.grupoK.grpc.DonacionId;
import com.grupoK.grpc.DonacionIdUsu;
import com.grupoK.grpc.DonacionList;
import com.grupoK.grpc.Empty;
import com.grupoK.grpc.ManagerServiceGrpc;
import com.grupoK.grpc.UserId;
import com.grupoK.grpc.UserUsername;
import com.grupoK.grpc.UsuarioList;

import io.grpc.stub.StreamObserver;

@GrpcService
public class ManagerServiceImpl extends ManagerServiceGrpc.ManagerServiceImplBase{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioWrapper usuarioWrapper;
	@Autowired
	private DonacionService donacionService;
	@Autowired
	private DonacionWrapper donacionWrapper;
	
	@Override
	public void getUserById(UserId request, StreamObserver<com.grupoK.grpc.Usuario> responseObserver) {
        // request -> DB -> map response -> return
		Usuario user = usuarioService.findById(request.getId());
        responseObserver.onNext(user != null ? usuarioWrapper.toGrpcUsuario(user) : null);
        responseObserver.onCompleted();
	}
	
	@Override
	public void getUserByUsername(UserUsername request, StreamObserver<com.grupoK.grpc.Usuario> responseObserver) {
        // request -> DB -> map response -> return
		Usuario user = usuarioService.findByUsername(request.getUsername());
        responseObserver.onNext(user != null ? usuarioWrapper.toGrpcUsuario(user) : null);
        responseObserver.onCompleted();
	}
	
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

	@Override
    public void deleteUser(UserId request, StreamObserver<com.grupoK.grpc.Usuario> responseObserver) {
		Usuario userDelete = usuarioService.delete(request.getId());
		responseObserver.onNext(usuarioWrapper.toGrpcUsuario(userDelete));
		responseObserver.onCompleted();
    }
	
	@Override
	public void insertOrUpdateDonacion(com.grupoK.grpc.Donacion request, StreamObserver<com.grupoK.grpc.Donacion> responseObserver) {
        // request -> DB -> map response -> return
		try {
		Donacion newDonacion = donacionService.saveOrUpdate(donacionWrapper.toEntityDonacion(request));
        responseObserver.onNext(donacionWrapper.toGrpcDonacion(newDonacion));
        responseObserver.onCompleted();
		}
		catch (Exception e) {
			responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
		                .withDescription(e.getMessage())
		                .asRuntimeException()
		        );
		}
	}
	
	@Override
	public void getDonacionById(DonacionId request, StreamObserver<com.grupoK.grpc.Donacion> responseObserver) {
        // request -> DB -> map response -> return
		try {
	    Donacion donacion = donacionService.findById(request.getId());
        responseObserver.onNext(donacion != null ? donacionWrapper.toGrpcDonacion(donacion) : null);
        responseObserver.onCompleted();
		}
		catch (Exception e) {
			responseObserver.onError(io.grpc.Status.NOT_FOUND
		                .withDescription(e.getMessage())
		                .asRuntimeException()
		        );
		}
	}
	
    @Override
    public void getAllDonaciones(Empty request, StreamObserver<DonacionList> responseObserver) {

        // request -> DB -> map response -> return
        List<Donacion> donaciones = donacionService.findAll();

        DonacionList response = DonacionList.newBuilder()
                .addAllDonacion(donaciones.stream()
                		.map(donacionWrapper::toGrpcDonacion).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
	@Override
    public void deleteDonacion(DonacionIdUsu request, StreamObserver<com.grupoK.grpc.Donacion> responseObserver) {
		try {
			System.out.println("usu"+request.getUsuario());
		Donacion donacionDelete = donacionService.delete(request.getId(), usuarioWrapper.toEntityUsuario(request.getUsuario()));
		responseObserver.onNext(donacionWrapper.toGrpcDonacion(donacionDelete));
		responseObserver.onCompleted();
		}
		catch (Exception e) {
			responseObserver.onError(io.grpc.Status.NOT_FOUND
		                .withDescription(e.getMessage())
		                .asRuntimeException()
		        );
		}
    }
	
}
