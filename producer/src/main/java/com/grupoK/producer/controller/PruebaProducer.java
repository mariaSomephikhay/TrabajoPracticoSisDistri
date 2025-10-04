package com.grupoK.producer.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupoK.producer.service.Producer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/prueba")
public class PruebaProducer {
	
	@Autowired
	Producer producerService;
	
	@PostMapping("/producer")
	public void producerMsg(@RequestBody String message) {
		producerService.sendMsgToTopic(message);
	}
}
