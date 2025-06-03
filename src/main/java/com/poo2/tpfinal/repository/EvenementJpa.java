package com.poo2.tpfinal.repository;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_evenement")
@Table(name = "evenement")
public class EvenementJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ev")
    private Long id_ev;

    private String nom;
    private String lieu;
    private LocalDate date;
    private int capacite_max;
    private String description;
    private String visibilite;
    private String image_url;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganisationJpa> organisations = new ArrayList<>();

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipationJpa> participations = new ArrayList<>();

    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addOrganisation(OrganisationJpa organisation) {
        organisations.add(organisation);
        organisation.setEvenement(this);
    }

    public void removeOrganisation(OrganisationJpa organisation) {
        organisations.remove(organisation);
        organisation.setEvenement(null);
    }
}