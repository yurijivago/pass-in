package com.sytecnologias.passin.services;

import com.sytecnologias.passin.domain.attendee.Attendees;
import com.sytecnologias.passin.domain.checkin.CheckIn;
import com.sytecnologias.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import com.sytecnologias.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendees attendees){
        this.verifyCheckInExists(attendees.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendees);
        newCheckIn.setCreatedAt(LocalDateTime.now());
        this.checkInRepository.save(newCheckIn);
    }

    public void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already check in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
