package com.grupoK.consumer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.Categoria;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.serviceImp.OrganizacionService;
import com.grupoK.connector.database.serviceImp.SolicitudService;
import com.grupoK.connector.database.serviceImp.UsuarioService;
import com.grupoK.consumer.modelDto.SolicitudDto;
import com.grupoK.consumer.modelDto.DonacionDto;
import com.grupoK.consumer.modelDto.SolicitudBajaDto;

@Service
public class ConsumerSolicitud {
	
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private OrganizacionService organizacionService;
	
	@Autowired
	private UsuarioService usuarioService;
	
    @KafkaListener(topicPattern = "_solicitud-donaciones_", groupId = "grupo_k")
    public void consumeSolicitud(ConsumerRecord<String, String> record) {
        System.out.println("Transferencia recibida desde " + record.topic());
        System.out.println("Contenido: " + record.value());
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            // Convertir JSON a entidad
            SolicitudDto solicitud = objectMapper.readValue(record.value(), SolicitudDto.class);
            
            Solicitud solicitudEntidad = new Solicitud(
            		solicitud.getId(), organizacionService.findById(solicitud.getIdOrganizacionSolicitante()), true, false, null);
            
            List<SolicitudDonacion> listaDonaciones = new ArrayList<>();
            for (DonacionDto d : solicitud.getDonaciones()) {
            	Donacion nueva = new Donacion(null, new Categoria(d.getCategoria().getId(), d.getCategoria().getDescripcion()), d.getDescripcion(), d.getCantidad(), 
            			false, null, usuarioService.findByUsername("Kafka"), null, usuarioService.findByUsername("Kafka"), null);
            	SolicitudDonacion soliDon = new SolicitudDonacion(null, solicitudEntidad, nueva);
            	listaDonaciones.add(soliDon);
            }
            
            // Guardar en la base de datos
            solicitudService.saveOrUpdate(solicitudEntidad, listaDonaciones);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //-------LOG para demostrar que otros grupos pueden consumir los mismos mesnajes a la vez
    @KafkaListener(topicPattern = "_solicitud-donaciones_", groupId = "Nova_Trend")
    public void consumeSolicitudNovaTrend(ConsumerRecord<String, String> record) {
        System.out.println("Nova Trend - Transferencia recibida desde " + record.topic());
        System.out.println("Nova Trend - Contenido: " + record.value());
    }
    
    @KafkaListener(topicPattern = "_solicitud-donaciones_", groupId = "Esencia Viva")
    public void consumeSolicitudEsenciaViva(ConsumerRecord<String, String> record) {
        System.out.println("Esencia Viva - Transferencia recibida desde " + record.topic());
        System.out.println("Esencia Viva - Contenido: " + record.value());
    }
    //---FIN DE LOG para demostrar que otros grupos pueden consumir los mismos mesnajes a la vez
  
	@KafkaListener(topicPattern = "_baja-solicitud-donaciones_", groupId = "grupo_k")
	public void consumeBajaEvento(ConsumerRecord<String, String> record) {
	    System.out.println("Transferencia recibida desde " + record.topic());
	    System.out.println("Contenido: " + record.value());

	    ObjectMapper objectMapper = new ObjectMapper();

	    try {
	    	SolicitudBajaDto solicitud = objectMapper.readValue(record.value(), SolicitudBajaDto.class);

	        solicitudService.delete(solicitud.getId());
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
