package com.sytecnologias.passin.services;

import com.sytecnologias.passin.domain.attendee.Attendees;
import com.sytecnologias.passin.domain.event.Events;
import com.sytecnologias.passin.domain.event.exceptions.EventFullException;
import com.sytecnologias.passin.domain.event.exceptions.EventNotFoundException;
import com.sytecnologias.passin.dto.attendee.AttendeeIdDTO;
import com.sytecnologias.passin.dto.attendee.AttendeeRequestDTO;
import com.sytecnologias.passin.dto.event.EventIdDTO;
import com.sytecnologias.passin.dto.event.EventRequestDTO;
import com.sytecnologias.passin.dto.event.EventResponseDTO;
import com.sytecnologias.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Events event = this.getEventById(eventId);
        List<Attendees> attendeesList = attendeeService.getAllAttendeesFromEvents(eventId);
        return new EventResponseDTO(event, attendeesList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Events newEvent = new Events();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());

    }

    public AttendeeIdDTO registrarAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO){
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Events event = this.getEventById(eventId);
        List<Attendees> attendeesList = attendeeService.getAllAttendeesFromEvents(eventId);

        if(event.getMaximumAttendees() <= attendeesList.size()) throw new EventFullException("Event is full");

        Attendees newAttendee = new Attendees();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());

    }

    private Events getEventById(String eventId){
        return  this.eventRepository.findById(eventId)
                .orElseThrow( () -> new EventNotFoundException("Event not found with ID " + eventId));
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
