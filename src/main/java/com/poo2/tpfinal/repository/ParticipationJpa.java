package com.poo2.tpfinal.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Entity
@IdClass(ParticipationJpa.ParticipationId.class)
public class ParticipationJpa {
    @Id
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private ParticipantJpa participant;

    @Id
    @ManyToOne
    @JoinColumn(name = "evenement_id")
    private EvenementJpa evenement;

    private String role;    //role = participant, artiste, intervenant

    @Data
    @EqualsAndHashCode
    public static class ParticipationId implements Serializable {
        private Long participant;
        private Long evenement;
    }
}
