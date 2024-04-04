package com.sytecnologias.passin.repositories;

import com.sytecnologias.passin.domain.event.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Events, String> {
}
