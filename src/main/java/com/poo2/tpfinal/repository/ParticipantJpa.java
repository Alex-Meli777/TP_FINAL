package com.poo2.tpfinal.repository;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ParticipantJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_par;

    private String nom;
    private String email;

    @OneToMany(mappedBy = "participant")
    private List<ParticipationJpa> participations;
}
