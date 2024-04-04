package com.sytecnologias.passin.services;

import com.sytecnologias.passin.domain.attendee.Attendees;
import com.sytecnologias.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.sytecnologias.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.sytecnologias.passin.domain.checkin.CheckIn;
import com.sytecnologias.passin.dto.attendee.AttendeeBagdeResponseDTO;
import com.sytecnologias.passin.dto.attendee.AttendeeDetail;
import com.sytecnologias.passin.dto.attendee.AttendeesListResponseDTO;
import com.sytecnologias.passin.dto.attendee.AttendeeBadgeDTO;
import com.sytecnologias.passin.repositories.AttendeesRepository;
import com.sytecnologias.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeesRepository attendeesRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendees> getAllAttendeesFromEvents(String eventId){
        return this.attendeesRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendees> attendeesList = this.getAllAttendeesFromEvents(eventId);

        List<AttendeeDetail> attendeeDetailsList = attendeesList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetail(attendee.getId(), attendee.getName(),
                    attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendees> isAttendeeRegister = this.attendeesRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegister.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registred.");
    }

    public Attendees registerAttendee(Attendees newAttendee){
        this.attendeesRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBagdeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendees attendees = attendeesRepository.findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID " + attendeeId));
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendees.getName(), attendees.getEmail(), uri, attendees.getEvent().getId());
        return new AttendeeBagdeResponseDTO(attendeeBadgeDTO);
    }

}
