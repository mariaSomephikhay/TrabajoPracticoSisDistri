package com.grupoK.producer.controller;

import com.grupoK.producer.service.Producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donation")
public class DonationController {
    @Autowired
    Producer producerService;

    @PostMapping("/transfer/{id_organization}/new")
    public ResponseEntity<Void> producerTransferMsg(@PathVariable("id_organization") Long idOrganization, @RequestBody String message) {
        String topic = "transferencia-donaciones_" + idOrganization;
        try{
        	producerService.sendMsgToTopic(topic, message);
        }catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/offer")
    public void producerOfferMsg(@RequestBody String message) {
    	try {
    		producerService.sendMsgToTopic("oferta-donaciones", message);
	    }catch (Exception e) {
	    	
	    }
    }
    
    @PostMapping("/request/new")
    public ResponseEntity<Object> producerRequestDoantion(@RequestBody String message) {
        String topic = "_solicitud-donaciones_";
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
