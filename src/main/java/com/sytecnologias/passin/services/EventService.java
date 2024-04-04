package com.sytecnologias.passin.services;

import com.sytecnologias.passin.domain.attendee.Attendees;
import com.sytecnologias.passin.domain.event.Events;
import com.sytecnologias.passin.domain.event.exceptions.EventNotFoundException;
import com.sytecnologias.passin.dto.event.EventIdDTO;
import com.sytecnologias.passin.dto.event.EventRequestDTO;
import com.sytecnologias.passin.dto.event.EventResponseDTO;
import com.sytecnologias.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Events event = this.eventRepository.findById(eventId)
                .orElseThrow( () -> new EventNotFoundException("Event not found with ID " + eventId));
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

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
