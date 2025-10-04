package com.grupoK.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMsgToTopic(String message) {
		System.out.println("msj" + message);
		kafkaTemplate.send("prueba", message);
	}
	
}
