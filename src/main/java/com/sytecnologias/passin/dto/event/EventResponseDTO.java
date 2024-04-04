package com.sytecnologias.passin.dto.event;

import com.sytecnologias.passin.domain.event.Events;
import lombok.Getter;

@Getter
public class EventResponseDTO {
    EventDetailDTO event;

    public EventResponseDTO(Events event, Integer numberOfAttenddes){
        this.event = new EventDetailDTO(
                event.getId(), event.getTitle(),
                event.getDetails(), event.getSlug(),
                event.getMaximumAttendees(), numberOfAttenddes);
    }
}
