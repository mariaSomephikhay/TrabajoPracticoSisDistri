package com.grupoK.producer.controller;

import com.grupoK.producer.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donation")
public class DonationController {
    @Autowired
    Producer producerService;

    @PostMapping("/transfer/{id_organization}/new")
    public void producerTransferMsg(@PathVariable("id_organization") Long idOrganization, @RequestBody String message) {
        String topic = "transferencia-donaciones_" + idOrganization;
        producerService.sendMsgToTopic(topic, message);
    }

    @GetMapping("/offer")
    public void producerOfferMsg(@RequestBody String message) {
        producerService.sendMsgToTopic("oferta-donaciones", message);
    }

}
