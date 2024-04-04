package com.sytecnologias.passin.domain.checkin;

import com.sytecnologias.passin.domain.attendee.Attendees;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "attendees_id", nullable = false)
    private Attendees attendee;
}
