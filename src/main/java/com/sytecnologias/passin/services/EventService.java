package com.sytecnologias.passin.services;

import com.sytecnologias.passin.domain.attendee.Attendees;
import com.sytecnologias.passin.domain.event.Events;
import com.sytecnologias.passin.domain.event.exceptions.EventNotFoundException;
import com.sytecnologias.passin.dto.EventIdDTO;
import com.sytecnologias.passin.dto.EventRequestDTO;
import com.sytecnologias.passin.dto.EventResponseDTO;
import com.sytecnologias.passin.repositories.AttendeesRepository;
import com.sytecnologias.passin.repositories.EventRepository;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeesRepository attendeesRepository;

    public EventResponseDTO getEventDetail(String eventId){
        Events event = this.eventRepository.findById(eventId)
                .orElseThrow( () -> new EventNotFoundException("Event not found with ID " + eventId));
        List<Attendees> attendeesList = this.attendeesRepository.findByEventId(eventId);
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
