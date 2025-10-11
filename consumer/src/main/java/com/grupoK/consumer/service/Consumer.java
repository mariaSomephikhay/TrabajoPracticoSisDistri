package com.grupoK.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topicPattern = "transferencia-donaciones_.*", groupId = "grupo_transferencias")
    public void consumeTransfers(ConsumerRecord<String, String> record) {
        System.out.println("📦 Transferencia recibida desde " + record.topic());
        System.out.println("➡️ Contenido: " + record.value());

        // 👉 acá procesás el JSON:
        // - parsear con Jackson o Gson
        // - actualizar inventario local (sumar o restar)
    }


    @KafkaListener(topics = "oferta-donaciones", groupId = "grupo_ofertas")
    public void consumeOffers(ConsumerRecord<String, String> record) {
        System.out.println("📦 Oferta de donaciones recibida desde " + record.topic());
        System.out.println("➡️ Contenido: " + record.value());

        // 👉 acá procesás el JSON para mostrar las donaciones disponibles
        // - guardarlas en BD si querés poder consultarlas
    }
}
