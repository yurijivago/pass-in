package com.sytecnologias.passin.repositories;

import com.sytecnologias.passin.domain.attendee.Attendees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeesRepository extends JpaRepository<Attendees, String> {
    public List<Attendees> findByEventId(String eventId);
}
