package com.sytecnologias.passin.repositories;

import com.sytecnologias.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
