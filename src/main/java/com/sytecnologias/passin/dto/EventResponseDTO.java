package com.sytecnologias.passin.dto;

import com.sytecnologias.passin.domain.event.Event;

public class EventResponseDTO {
    EventDetailDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttenddes){
        this.event = new EventDetailDTO(
                event.getId(), event.getTitle(),
                event.getDetails(), event.getSlug(),
                event.getMaximumAttendees(), numberOfAttenddes);
    }
}
