package com.grupoK.connector.database.serviceImp;

import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.repositories.IEventoRepository;
import com.grupoK.connector.database.service.IEventoService;
import com.grupoK.connector.database.service.IUsuarioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@GrpcServerAnnotation
@Service
public class EventoService implements IEventoService {

	@Autowired
	private IEventoRepository eventoRepository;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public Evento findByNombre(String nombre) throws Exception {
		return eventoRepository.findByNombre(nombre)
		        .orElseThrow(() -> new Exception("No se encontró evento"));
	}

	@Override
	public Evento saveOrUpdate(Evento evento) throws Exception{
		
//		if(evento.getId() == 0 || evento.getId() == null) {
//			evento.setId(null); //Proto lo devuelve con un 0
//			evento.setIdUsuarioAlta(evento.getIdUsuarioAlta());
//        	eventoRepository.save(evento);
//        }
//        else {
		
		try {
			Evento eventoActual = this.findById(evento.getId());
			map(eventoActual, evento);
			eventoRepository.save(eventoActual);
		}catch (Exception e){
			eventoRepository.save(evento);
		}
        
        return evento;
	}

	@Override
	public Evento findById(String id) throws Exception {
		return eventoRepository.findById(id)
			.orElseThrow(() -> new Exception("No se encontró evento"));
	}
	
	private void map(Evento preUpdated, Evento updated) {
		if(!StringUtils.isEmpty(updated.getDescripcion()))
			preUpdated.setDescripcion(updated.getDescripcion());
		if(!StringUtils.isEmpty(updated.getNombre()))
			preUpdated.setNombre(updated.getNombre());
		if(updated.getFecha() != null)
			preUpdated.setFecha(updated.getFecha());
		preUpdated.setPublicado(updated.getPublicado());
	}

	@Override
	public Evento detele(Evento evento) throws Exception {
		if(evento.getFecha().isAfter(LocalDateTime.now())) {
			eventoRepository.delete(evento);
		}else {
			throw new Exception ("La fecha del evento es igual al dia de hoy o anterior");
		}
		
		return evento;
	}
	
	@Override
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	@Override
	public List<Usuario> saveUsersToEvento(Evento evento, List<Integer> lstUsersId) {
		List<Usuario> lstUsers = usuarioService.getUsersById(lstUsersId);
		evento.setUsuarios(lstUsers);
		eventoRepository.save(evento);
		return lstUsers;
	}

	@Override
	public List<Usuario> getUsersByIdEvento(String id) {
		return eventoRepository.findUsuariosByEventoId(id);
	}

	

	

}
