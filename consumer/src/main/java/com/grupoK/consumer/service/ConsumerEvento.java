package com.grupoK.consumer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.entities.Voluntario;
import com.grupoK.connector.database.exceptions.EventoNoEncontradoException;
import com.grupoK.connector.database.exceptions.UserNotFoundException;
import com.grupoK.connector.database.exceptions.VoluntarioNotFoundException;
import com.grupoK.connector.database.serviceImp.EventoService;
import com.grupoK.connector.database.serviceImp.OrganizacionService;
import com.grupoK.connector.database.serviceImp.UsuarioService;
import com.grupoK.connector.database.serviceImp.VoluntarioService;
import com.grupoK.consumer.modelDto.AdhesionEventoDto;
import com.grupoK.consumer.modelDto.EventoBajaDto;
import com.grupoK.consumer.modelDto.EventoDto;
import com.grupoK.consumer.modelDto.VoluntarioDto;

@Service
public class ConsumerEvento {
	
	@Autowired
	private EventoService eventoService;
	@Autowired
	private OrganizacionService organizacionService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private VoluntarioService voluntarioService;
	
	@KafkaListener(topicPattern = "_eventos-solidarios_", groupId = "grupo_k")
	public void consumeEvento(ConsumerRecord<String, String> record) {
	    System.out.println("Transferencia recibida desde " + record.topic());
	    System.out.println("Contenido: " + record.value());

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.registerModule(new JavaTimeModule());
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	    try {
	        EventoDto evento = objectMapper.readValue(record.value(), EventoDto.class);

	        if (!evento.getIdOrganizacion().equals("1")) {
	            Evento entityEvento = new Evento(
	                evento.getId(),
	                evento.getNombre(),
	                evento.getDescripcion(),
	                evento.getFecha(),
	                null, null,
	                usuarioService.findById("GK-20251017133221-5555"), //usuario de alta de kafka
	                organizacionService.findById(Integer.parseInt(evento.getIdOrganizacion())),
	                true,
	                false,
	                null
	            );

	            eventoService.saveOrUpdate(entityEvento);
	        } else {
	            System.out.println("Evento de nuestra organizacion, no se guarda idEvento " + evento.getId());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@KafkaListener(topicPattern = "_baja-evento-solidario_", groupId = "grupo_k")
	public void consumeBajaEvento(ConsumerRecord<String, String> record) {
	    System.out.println("Transferencia recibida desde " + record.topic());
	    System.out.println("Contenido: " + record.value());

	    ObjectMapper objectMapper = new ObjectMapper();

	    try {
	    	EventoBajaDto evento = objectMapper.readValue(record.value(), EventoBajaDto.class);

	        if (!evento.getIdOrganizacion().equals("1")) {
	        	eventoService.detele(eventoService.findById(evento.getId()));
	        } else {
	            System.out.println("Evento de nuestra organizacion, no se elimina idEvento " + evento.getId());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@KafkaListener(topicPattern = "adhesion-evento_1", groupId = "grupo_k")
	public void consumeUsersToEvent(ConsumerRecord<String, String> record) {
	    System.out.println("Transferencia recibida desde " + record.topic());
	    System.out.println("Contenido: " + record.value());

	    ObjectMapper objectMapper = new ObjectMapper();

	    try {
	    	AdhesionEventoDto adhesionEventoDto = objectMapper.readValue(record.value(), AdhesionEventoDto.class);
	    	
	    	Evento evento = eventoService.findByIdWithVoluntarios(adhesionEventoDto.getIdEvento());
	    	
	    	List<Voluntario> voluntarios = evento.getVoluntarios();
	    	
	    	VoluntarioDto voluntario = adhesionEventoDto.getVoluntario();
	    	Voluntario newVoluntario= new Voluntario();
	    	
	    	try {
	    		newVoluntario = voluntarioService.findById(voluntario.getIdVoluntario());
	    	}catch (VoluntarioNotFoundException e){
	    		newVoluntario.setOrganizacion(organizacionService.findById(Integer.parseInt(voluntario.getIdOrganizacion())));
	    		newVoluntario.setId(voluntario.getIdVoluntario());
	    		newVoluntario.setNombre(voluntario.getNombre());
	    		newVoluntario.setApellido(voluntario.getApellido());
	    		newVoluntario.setTelefono(voluntario.getTelefono());
	    		newVoluntario.setEmail(voluntario.getEmail());
	    		voluntarioService.saveOrUpdate(newVoluntario);
	    	}
	    	
	    	voluntarios.add(newVoluntario);
	    	
	    	List<Voluntario> voluntariosUnicos = new ArrayList<>();
	    	
	    	if (voluntarios.size()>0) {
	    	    voluntariosUnicos = new ArrayList<>(
	    	        voluntarios.stream()
	    	            .collect(Collectors.toMap(
	    	                Voluntario::getId,
	    	                v -> v,
	    	                (v1, v2) -> v1
	    	            ))
	    	            .values()
	    	    );
	    	}else {
	    		voluntariosUnicos.add(newVoluntario);
	    	}
	    	
	    	eventoService.saveVoluntariosToEvento(evento, voluntariosUnicos);
	        
	    } catch (EventoNoEncontradoException e) {
	        System.out.println("Evento no encontrado en el sistema");
	    }catch (Exception e) {
	        System.out.println("Ocurrio un error inesperado: " + e.getMessage());
	    }
	}
}
