package com.sytecnologias.passin.repositories;

import com.sytecnologias.passin.domain.attendee.Attendees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendeesRepository extends JpaRepository<Attendees, String> {
    List<Attendees> findByEventId(String eventId);
    Optional<Attendees> findByEventIdAndEmail(String eventId, String email);
}
