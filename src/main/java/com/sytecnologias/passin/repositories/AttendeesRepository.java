package com.sytecnologias.passin.repositories;

import com.sytecnologias.passin.domain.attendee.Attendees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeesRepository extends JpaRepository<Attendees, String> {
}
