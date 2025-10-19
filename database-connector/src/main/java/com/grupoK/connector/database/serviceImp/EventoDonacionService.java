package com.grupoK.connector.database.serviceImp;


import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.EventoDonacion;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.repositories.IEventoDonacionRepository;
import com.grupoK.connector.database.service.IDonacionService;
import com.grupoK.connector.database.service.IEventoDonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@GrpcServerAnnotation
@Service
public class EventoDonacionService implements IEventoDonacionService {
	@Autowired
	private IEventoDonacionRepository repository;
	@Autowired
	private IDonacionService donacionService;
	
	@Override
	public boolean insertEventoDonacion(Evento evento, Donacion donacion,
                                        int cantDonacion, Usuario user) throws Exception {
		
		Optional <EventoDonacion> eventoDonacion = repository.findByEvento_IdAndDonacion_Id(evento.getId(), donacion.getId());
		int totalDonacionRestante = 0;
		int cantidadAnterior = 0;
		
		if(eventoDonacion.isPresent()) {
			EventoDonacion updEventoDonacion = eventoDonacion.get();
			cantidadAnterior = updEventoDonacion.getCantRepartida();
			
			updEventoDonacion.setCantRepartida(cantDonacion);
			updEventoDonacion.setFechaModificacion(LocalDateTime.now());
			updEventoDonacion.setUsuarioModificacion(user);
			repository.save(updEventoDonacion);
			
			totalDonacionRestante = (donacion.getCantidad()+cantidadAnterior)-cantDonacion;
		}else {
			EventoDonacion newEventoDonacion = map(evento,donacion,cantDonacion,user);
			repository.save(newEventoDonacion);
			totalDonacionRestante = donacion.getCantidad()-cantDonacion;
		}
		
		donacion.setCantidad(totalDonacionRestante);

		donacionService.saveOrUpdate(donacion);
		
		return true;
	}
	
	private EventoDonacion map(Evento evento, Donacion donacion, 
										Integer cantDonacion, Usuario user) throws Exception {
		
		EventoDonacion newEventoDonacion = new EventoDonacion();
		
		newEventoDonacion.setEvento(evento);
		newEventoDonacion.setDonacion(donacion);
		newEventoDonacion.setCantRepartida(cantDonacion);
		newEventoDonacion.setIdUsuarioAlta(user);
		newEventoDonacion.setUsuarioModificacion(user);
		
		return  newEventoDonacion;
	}

	@Override
	public List<EventoDonacion> getEventoWithDonacionByIdEvento(String idEvento) {
		Optional<List<EventoDonacion>> lst = repository.findByEvento_Id(idEvento);
		return lst.get();
	}
}
