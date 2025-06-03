package com.poo2.tpfinal.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "organisateur")
public class OrganisateurJpa extends ParticipantJpa {
    @Column(name = "nom_utilisateur", unique = true, nullable = false, length = 50)
    private String nom_utilisateur;

    @Column(name = "mot_de_passe", nullable = false, length = 100)
    private String mot_de_passe;

    @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganisationJpa> organisations = new ArrayList<>();

    // Constructeur par défaut requis par JPA
    public OrganisateurJpa() {
        super();
    }

    // Constructeur avec paramètres
    public OrganisateurJpa(String nom, String email, String nomUtilisateur, String motDePasse) {
        this.setNom(nom);
        this.setEmail(email);
        this.nom_utilisateur = nomUtilisateur;
        this.mot_de_passe = motDePasse;
    }


    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addOrganisation(OrganisationJpa organisation) {
        organisations.add(organisation);
        organisation.setOrganisateur(this);
    }

    public void removeOrganisation(OrganisationJpa organisation) {
        organisations.remove(organisation);
        organisation.setOrganisateur(null);
    }
}