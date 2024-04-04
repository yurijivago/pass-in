package com.sytecnologias.passin.repositories;

import com.sytecnologias.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
