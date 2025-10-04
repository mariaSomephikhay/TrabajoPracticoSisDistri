package com.grupoK.consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	
	@KafkaListener(topics = "prueba", groupId= "codes")
	public void consumerTopic(String mensaje) {
		System.out.println("msj recibido: " + mensaje);
	}
}
