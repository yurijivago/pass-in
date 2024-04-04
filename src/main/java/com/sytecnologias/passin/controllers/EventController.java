package com.sytecnologias.passin.controllers;

import com.sytecnologias.passin.dto.attendee.AttendeesListResponseDTO;
import com.sytecnologias.passin.dto.event.EventIdDTO;
import com.sytecnologias.passin.dto.event.EventRequestDTO;
import com.sytecnologias.passin.dto.event.EventResponseDTO;
import com.sytecnologias.passin.services.AttendeeService;
import com.sytecnologias.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String eventId){
        AttendeesListResponseDTO attendeesListRespose = this.attendeeService.getEventsAttendee(eventId);
        return ResponseEntity.ok(attendeesListRespose);
    }


}
