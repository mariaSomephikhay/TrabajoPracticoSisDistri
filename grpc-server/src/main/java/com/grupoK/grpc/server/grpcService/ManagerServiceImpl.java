package com.grupoK.grpc.server.grpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.EventoDonacion;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.exceptions.UserEmailAlreadyExistsException;
import com.grupoK.connector.database.exceptions.UserNotFoundException;
import com.grupoK.connector.database.exceptions.UserUsernameAlreadyExistsException;
import com.grupoK.connector.database.serviceImp.DonacionService;
import com.grupoK.connector.database.serviceImp.EventoDonacionService;
import com.grupoK.connector.database.serviceImp.EventoService;
import com.grupoK.connector.database.serviceImp.SolicitudService;
import com.grupoK.connector.database.serviceImp.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import com.grupoK.grpc.server.wrappers.DonacionWrapper;
import com.grupoK.grpc.server.wrappers.EventoWrapper;
import com.grupoK.grpc.server.wrappers.SolicitudWrapper;
import com.grupoK.grpc.server.wrappers.UsuarioWrapper;
import com.grupoK.grpc.proto.DonacionId;
import com.grupoK.grpc.proto.DonacionIdUsu;
import com.grupoK.grpc.proto.DonacionList;
import com.grupoK.grpc.proto.DonacionesAsociadas;
import com.grupoK.grpc.proto.Empty;
import com.grupoK.grpc.proto.EventoId;
import com.grupoK.grpc.proto.EventoList;
import com.grupoK.grpc.proto.EventoWithAllListDonacionesDetails;
import com.grupoK.grpc.proto.EventoWithListDonacionesDetails;
import com.grupoK.grpc.proto.EventoWithListUsersDetails;
import com.grupoK.grpc.proto.ListSolicitudDonacion;
import com.grupoK.grpc.proto.ManagerServiceGrpc;
import com.grupoK.grpc.proto.UserId;
import com.grupoK.grpc.proto.UserUsername;
import com.grupoK.grpc.proto.UsuarioList;
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
	
	@Autowired
	private EventoService eventoService;
	@Autowired
	private EventoWrapper eventoWrapper;
	
	@Autowired
	private EventoDonacionService eventoDonacionService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private SolicitudWrapper solicitudWrapper;
	
	@Override
	public void getUserById(UserId request, StreamObserver<com.grupoK.grpc.proto.Usuario> responseObserver) {
        // request -> DB -> map response -> return
		try {
			Usuario user = usuarioService.findById(request.getId());
	        responseObserver.onNext(usuarioWrapper.toGrpcUsuario(user));
	        responseObserver.onCompleted();
		} catch (UserNotFoundException e) {
	        responseObserver.onError(io.grpc.Status.NOT_FOUND
	                .withDescription(e.getMessage())
	                .asRuntimeException());
		} catch (Exception e) {
	        responseObserver.onError(io.grpc.Status.INTERNAL
	                .withDescription("Internal server error: " + e.getMessage())
	                .asRuntimeException());
		}
	}
	
	@Override
	public void getUserByUsername(UserUsername request, StreamObserver<com.grupoK.grpc.proto.Usuario> responseObserver) {
        // request -> DB -> map response -> return
		try {
			Usuario user = usuarioService.findByUsername(request.getUsername());
	        responseObserver.onNext(usuarioWrapper.toGrpcUsuario(user));
	        responseObserver.onCompleted();
		} catch (UserNotFoundException e) {
	        responseObserver.onError(io.grpc.Status.NOT_FOUND
	                .withDescription(e.getMessage())
	                .asRuntimeException());
		} catch (Exception e) {
	        responseObserver.onError(io.grpc.Status.INTERNAL
	                .withDescription("Internal server error: " + e.getMessage())
	                .asRuntimeException());
		}

	}
	
    @Override
    public void getAllUsers(Empty request, StreamObserver<UsuarioList> responseObserver) {
        // request -> DB -> map response -> return
    	try {
            List<Usuario> users = usuarioService.findAll();
            
            UsuarioList response = UsuarioList.newBuilder()
                    .addAllUsuarios(users.stream()
                    		.map(usuarioWrapper::toGrpcUsuario).toList())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
		} catch (Exception e) {
	        responseObserver.onError(io.grpc.Status.INTERNAL
	                .withDescription("Internal server error: " + e.getMessage())
	                .asRuntimeException());
		}

    }
	
	@Override
	public void insertOrUpdateUser(com.grupoK.grpc.proto.Usuario request, StreamObserver<com.grupoK.grpc.proto.Usuario> responseObserver) {
        // request -> DB -> map response -> return
	    try {
	        Usuario newUser = usuarioService.saveOrUpdate(usuarioWrapper.toEntityUsuario(request));
	        responseObserver.onNext(usuarioWrapper.toGrpcUsuario(newUser));
	        responseObserver.onCompleted();
	    } catch (UserUsernameAlreadyExistsException | UserEmailAlreadyExistsException e) {
	        responseObserver.onError(io.grpc.Status.ALREADY_EXISTS
	                .withDescription(e.getMessage())
	                .asRuntimeException());
	    } catch (UserNotFoundException e) {
	        responseObserver.onError(io.grpc.Status.NOT_FOUND
	                .withDescription(e.getMessage())
	                .asRuntimeException());
	    } catch (Exception e) {
	        // Error general
	        responseObserver.onError(io.grpc.Status.INTERNAL
	                .withDescription("Internal server error: " + e.getMessage())
	                .asRuntimeException());
	    }
	}

	@Override
    public void deleteUser(UserId request, StreamObserver<com.grupoK.grpc.proto.Usuario> responseObserver) {
		try {
			Usuario userDelete = usuarioService.delete(request.getId());
			responseObserver.onNext(usuarioWrapper.toGrpcUsuario(userDelete));
			responseObserver.onCompleted();
		} catch (UserNotFoundException e) {
	        responseObserver.onError(io.grpc.Status.NOT_FOUND
	                .withDescription(e.getMessage())
	                .asRuntimeException());
		} catch (Exception e) {
	        responseObserver.onError(io.grpc.Status.INTERNAL
	                .withDescription("Internal server error: " + e.getMessage())
	                .asRuntimeException());
		}

    }
	
	/**************************************************************************************************/
	
	@Override
	public void insertOrUpdateDonacion(com.grupoK.grpc.proto.Donacion request, StreamObserver<com.grupoK.grpc.proto.Donacion> responseObserver) {
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
	public void getDonacionById(DonacionId request, StreamObserver<com.grupoK.grpc.proto.Donacion> responseObserver) {
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
                .addAllDonaciones(donaciones.stream()
                		.map(donacionWrapper::toGrpcDonacion).toList())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
	@Override
    public void deleteDonacion(DonacionIdUsu request, StreamObserver<com.grupoK.grpc.proto.Donacion> responseObserver) {
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
	
	//------------EVENTO-----------//
		@Override
		public void insertOrUpdateEvento(com.grupoK.grpc.proto.Evento request, StreamObserver<com.grupoK.grpc.proto.Evento> responseObserver) {
			// request -> DB -> map response -> return
			Evento newEvento;
			try {
				newEvento = eventoService.saveOrUpdate(eventoWrapper.toEntityEvento(request));
				responseObserver.onNext(eventoWrapper.toGrpcEvento(newEvento));
			    responseObserver.onCompleted();
			} catch (Exception e) {
				responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
			              				.withDescription(e.getMessage())
			              				.asRuntimeException());
			}
		}
		
		@Override
		public void deleteEventos(com.grupoK.grpc.proto.EventoId request,io.grpc.stub.StreamObserver<com.grupoK.grpc.proto.Evento> responseObserver) {
			try {
				Evento evento = eventoService.findById(request.getId());
				eventoService.detele(evento);
				responseObserver.onNext(eventoWrapper.toGrpcEvento(evento));
			    responseObserver.onCompleted();
			} catch (Exception e) {
				responseObserver.onError(io.grpc.Status.FAILED_PRECONDITION
			              				.withDescription(e.getMessage())
			              				.asRuntimeException());
			}
		}
		
		@Override
		public void getEventoById(EventoId request, StreamObserver<com.grupoK.grpc.proto.Evento> responseObserver) {
			try {
				Evento evento = eventoService.findById(request.getId());
				responseObserver.onNext(eventoWrapper.toGrpcEvento(evento));
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
	    public void getAllEventos(Empty request, StreamObserver<EventoList> responseObserver) {

	        // request -> DB -> map response -> return
	        List<Evento> lstEventos = eventoService.findAll();

	        EventoList response = EventoList.newBuilder()
	                .addAllEventos(lstEventos.stream()
	                		.map(eventoWrapper::toGrpcEvento).toList())
	                .build();
	        responseObserver.onNext(response);
	        responseObserver.onCompleted();
	    }
		
		@Override
		public void insertUsersToEvento(com.grupoK.grpc.proto.EventoWithListUsers request, StreamObserver<com.grupoK.grpc.proto.EventoWithListUsersDetails> responseObserver) {
//	        // request -> DB -> map response -> return
			try {
				Evento evento = eventoService.findById(request.getId());
		    	List<UserId> listaUsuarios = request.getUsersIdsList();

		    	List<Integer> lstUsers = listaUsuarios.stream()
		    	        .map(UserId::getId)
		    	        .collect(Collectors.toList());

		    	List<Usuario> lstUsuarioEntidad = eventoService.saveUsersToEvento(evento, lstUsers);
		    	
		    	EventoWithListUsersDetails.Builder responseBuilder = EventoWithListUsersDetails.newBuilder();

		    	responseBuilder.setEvento(eventoWrapper.toGrpcEvento(evento));

		    	responseBuilder.addAllUsers(
		    	    lstUsuarioEntidad.stream()
		    	        .map(usuarioWrapper::toGrpcUsuario)
		    	        .toList()
		    	);

		    	responseObserver.onNext(responseBuilder.build());
		    	responseObserver.onCompleted();
		    } catch (Exception e) {
		        responseObserver.onError(io.grpc.Status.INTERNAL
		                .withDescription("Internal server error: " + e.getMessage())
		                .asRuntimeException());
		    }
		}
		
		@Override
		public void insertDonacionesToEvento(com.grupoK.grpc.proto.EventoWithListDonaciones request, StreamObserver<com.grupoK.grpc.proto.EventoWithListDonacionesDetails> responseObserver) {
			try {

				//MAPEO DE DATOS
				Evento evento = eventoService.findById(request.getIdEvento());

				Donacion donacion = donacionService.findById(request.getDonacionId());

				Usuario entityUsuario = usuarioWrapper.toEntityUsuario(request.getUsuario());

				//LLAMADO AL SERVICE
				eventoDonacionService.insertEventoDonacion(evento, donacion, request.getCantidad(), entityUsuario);

				//ARMADO DEL RESPONSE
				EventoWithListDonacionesDetails.Builder responseBuilder = EventoWithListDonacionesDetails.newBuilder();

				responseBuilder.setId(eventoWrapper.toGrpcEvento(evento));

				responseBuilder.setDonacion(donacionWrapper.toGrpcDonacion(donacion));

				responseBuilder.setCantidad(request.getCantidad());

				responseObserver.onNext(responseBuilder.build());
		    	responseObserver.onCompleted();
			} catch (Exception e) {
		        responseObserver.onError(io.grpc.Status.INTERNAL
		                .withDescription("Internal server error: " + e.getMessage())
		                .asRuntimeException());
		    }
		}

		@Override
		public void getEventoWithUsersById(EventoId request,
				StreamObserver<EventoWithListUsersDetails> responseObserver) {

			try {
				List<Usuario> lstUsuarios = eventoService.getUsersByIdEvento(request.getId());

				//ARMADO DEL RESPONSE
				Evento evento = eventoService.findById(request.getId());

				EventoWithListUsersDetails response = EventoWithListUsersDetails.newBuilder()
						.setEvento(eventoWrapper.toGrpcEvento(evento))
	                    .addAllUsers(lstUsuarios.stream()
	                    		.map(usuarioWrapper::toGrpcUsuario).toList())
	                    .build();
	            responseObserver.onNext(response);
	            responseObserver.onCompleted();

			}catch (Exception e) {
				responseObserver.onError(io.grpc.Status.INTERNAL
		                .withDescription("Internal server error: " + e.getMessage())
		                .asRuntimeException());
			}
		}

		@Override
		public void getEventoWithDonacionesById(EventoId request,
				StreamObserver<EventoWithAllListDonacionesDetails> responseObserver) {

			try {
				List<EventoDonacion> lst = eventoDonacionService.getEventoWithDonacionByIdEvento(request.getId());

				//ARMADO DEL RESPONSE
				Evento evento = eventoService.findById(request.getId());

				List<DonacionesAsociadas> donacionesAsociada = new ArrayList<>();

				for(EventoDonacion eD : lst) {
					DonacionesAsociadas donacionAsociada = DonacionesAsociadas.newBuilder()
						    .setDonacion(donacionWrapper.toGrpcDonacion(eD.getDonacion()))
						    .setCantidad(eD.getCantRepartida())
						    .build();
					donacionesAsociada.add(donacionAsociada);
				}

				EventoWithAllListDonacionesDetails response = EventoWithAllListDonacionesDetails.newBuilder()
					    .setId(eventoWrapper.toGrpcEvento(evento))
					    .addAllListaDonacion(donacionesAsociada)
					    .build();

				responseObserver.onNext(response);
		        responseObserver.onCompleted();

			}catch (Exception e) {
				responseObserver.onError(io.grpc.Status.INTERNAL
		                .withDescription("Internal server error: " + e.getMessage())
		                .asRuntimeException());
			}
		}

		
		@Override
		public void getAllSolicitudDonaciones(Empty request, StreamObserver<ListSolicitudDonacion> responseObserver) {
			
			try {
				List<Solicitud> solicitudes = solicitudService.findAll();
			
			/*ListSolicitudDonacion response = ListSolicitudDonacion.newBuilder()
					.addAllSolicitudes(solicitudes.stream()
	                		.map(solicitud -> solicitudWrapper.toGrpcSolicitudDonacion(solicitud, solicitudService.findAllDonationsAssociatedByRequest(solicitud)))
	                .build();
	                */
				
				ListSolicitudDonacion response = ListSolicitudDonacion.newBuilder()
					    .addAllSolicitudes(solicitudes.stream()
					            .map(solicitud -> {
					                try {
					                    // Obtener donaciones asociadas a esta solicitud
					                    List<SolicitudDonacion> donationsAssociated =
					                        solicitudService.findAllDonationsAssociatedByRequest(solicitud);
					                    
					                    List<Donacion> donaciones = donationsAssociated.stream()
					                            .map(SolicitudDonacion::getDonacion)
					                            .toList();
					                    // Mapear a gRPC usando el wrapper
					                    return solicitudWrapper.toGrpcSolicitudDonacion(solicitud, donaciones);
					                }catch (Exception e) {
					                	throw new RuntimeException("Error al obtener donaciones asociadas: " + e.getMessage(), e);
					    			}
					         
					            })
					            .toList()
					    )
					    .build();
			System.out.println(response);	
	        responseObserver.onNext(response);
	        responseObserver.onCompleted();
	        
			}catch (Exception e) {
				responseObserver.onError(io.grpc.Status.INTERNAL
		                .withDescription("Internal server error: " + e.getMessage())
		                .asRuntimeException());
			}
		}

}
