package com.grupoK.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.serviceImp.SolicitudService;
import com.grupoK.consumer.modelDto.SolicitudDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Consumer {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topicPattern = "transferencia-donaciones_.*", groupId = "grupo_transferencias")
    public void consumeTransfers(ConsumerRecord<String, String> record) {
        try {
            SolicitudDto solicitud = objectMapper.readValue(record.value(), SolicitudDto.class); //Proceso el mensaje

            Solicitud request = solicitudService.findById(solicitud.getIdSolicitud());
            List<SolicitudDonacion> donacionesAsociadas = solicitudService.findAllDonationsAssociatedByRequest(request);
            solicitudService.saveOrUpdate(request, donacionesAsociadas);

            System.out.println("Solicitud transferida exitosamente");

        }catch (Exception e) {
            System.err.println("Error procesando solicitud " + record.value() + ": " + e.getMessage());
        }
    }


    @KafkaListener(topics = "oferta-donaciones", groupId = "grupo_ofertas")
    public void consumeOffers(ConsumerRecord<String, String> record) {
        System.out.println("📦 Oferta de donaciones recibida desde " + record.topic());
        System.out.println("➡️ Contenido: " + record.value());

        // 👉 acá procesás el JSON para mostrar las donaciones disponibles
        // - guardarlas en BD si querés poder consultarlas
    }
}
