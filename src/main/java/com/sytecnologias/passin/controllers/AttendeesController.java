package com.sytecnologias.passin.controllers;

import com.sytecnologias.passin.dto.attendee.AttendeeBagdeResponseDTO;
import com.sytecnologias.passin.services.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeesController {
    private final AttendeeService attendeeService;
    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBagdeResponseDTO> getTesteAttendeesController(@PathVariable String attendeeId,
                                                              UriComponentsBuilder uriComponentsBuilder){
        AttendeeBagdeResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }
}
