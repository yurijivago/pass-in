package com.sytecnologias.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendees")
public class AttendeesController {
    @GetMapping()
    public ResponseEntity<String> getTesteAttendeesController(){
        return ResponseEntity.ok("Attendee controller -> Sucesso");
    }
}
