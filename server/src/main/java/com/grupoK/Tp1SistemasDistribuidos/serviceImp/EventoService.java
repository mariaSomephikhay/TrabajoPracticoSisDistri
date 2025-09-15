package com.grupoK.Tp1SistemasDistribuidos.serviceImp;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.Tp1SistemasDistribuidos.entities.Evento;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IEventoRepository;
import com.grupoK.Tp1SistemasDistribuidos.service.IEventoService;

@Service
public class EventoService implements IEventoService{

	@Autowired
	private IEventoRepository eventoRepository;
	
	@Override
	public Evento findByNombre(String nombre) throws Exception {
		return eventoRepository.findByNombre(nombre)
		        .orElseThrow(() -> new Exception("No se encontró evento"));
	}

	@Override
	public Evento saveOrUpdate(Evento evento) throws Exception{
		
		if(evento.getId() == 0 || evento.getId() == null) {
			evento.setId(null); //Proto lo devuelve con un 0
			evento.setIdUsuarioAlta(evento.getIdUsuarioAlta());
        	eventoRepository.save(evento);
        }
        else {
        	Evento eventoActual = (eventoRepository.findById(evento.getId())).get();
        	map(eventoActual, evento);
        	return eventoRepository.save(eventoActual);
        }
		return evento;
	}

	@Override
	public Evento findById(Integer id) throws Exception {
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

}
