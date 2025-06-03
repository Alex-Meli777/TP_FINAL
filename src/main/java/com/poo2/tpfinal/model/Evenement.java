package com.poo2.tpfinal.model;

import com.poo2.tpfinal.repository.OrganisationJpa;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class Evenement {
    @Column(unique = true)
    private String nom;
    private String lieu;
    private LocalDate date;
    private int capacite_max;
    private String description;
    private String visibilite;
    private String image_url;
    private Map<Organisateur, String> organisateurs;    //Organisateur, Createur/non
    private Map<Participant, String> participants;  //Participant, Role

    public TypeEvenement getType(){
        return null;
    }
}

