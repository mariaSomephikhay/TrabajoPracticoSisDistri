package com.grupoK.producer.controller;

import com.grupoK.producer.service.Producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    Producer producerService;

    
    @PostMapping("/request/new")
    public ResponseEntity<Object> producerRequestEvent(@RequestBody String message) {
        String topic = "_eventos-solidarios_";
        try {
        	producerService.sendMsgToTopic(topic, message);
        }catch (Exception e) {
        	Map<String, String> errorResponse = new HashMap<>();
        	errorResponse.put("error", e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
    
    @PostMapping("/request/delete")
    public ResponseEntity<Object> producerRequestDeleteEvent(@RequestBody String message) {
        String topic = "_baja-evento-solidario_";
        try {
        	producerService.sendMsgToTopic(topic, message);
        }catch (Exception e) {
        	Map<String, String> errorResponse = new HashMap<>();
        	errorResponse.put("error", e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

}
