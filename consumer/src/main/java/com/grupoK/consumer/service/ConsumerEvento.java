package com.grupoK.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.serviceImp.EventoService;
import com.grupoK.connector.database.serviceImp.OrganizacionService;
import com.grupoK.connector.database.serviceImp.UsuarioService;
import com.grupoK.consumer.modelDto.EventoBajaDto;
import com.grupoK.consumer.modelDto.EventoDto;

@Service
public class ConsumerEvento {
	
	@Autowired
	private EventoService eventoService;
	@Autowired
	private OrganizacionService organizacionService;
	
	@Autowired
	private UsuarioService usuarioService;
	
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
	                usuarioService.findById(5), //usuario de alta de kafka
	                organizacionService.findById(Integer.parseInt(evento.getIdOrganizacion())),
	                true,
	                false
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


}
