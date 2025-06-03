package com.poo2.tpfinal.util;

import com.poo2.tpfinal.model.Conference;
import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.model.TypeEvenement;
import com.poo2.tpfinal.repository.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConferenceGatewayStrategy implements EvenementGatewayStrategy<ConferenceJpa, Conference> {
    private final EvenementRepository evenementRepository;
    private final ParticipationRepository participationRepository;

    @Autowired
    public ConferenceGatewayStrategy(EvenementRepository evenementRepository, ParticipationRepository participationRepository) {
        this.evenementRepository = evenementRepository;
        this.participationRepository = participationRepository;
    }
    @Override
    public boolean peutGerer(TypeEvenement type) {
        return TypeEvenement.CONFERENCE == type;
    }

    @Override
    public Class<ConferenceJpa> getTypeJpa() {
        return ConferenceJpa.class;
    }

    @Override
    public EvenementJpa convertirVersJpa(Evenement evenement) {
        Conference Conference = (Conference) evenement;
        ConferenceJpa jpa = new ConferenceJpa();
        // Copie des champs communs
        jpa.setNom(Conference.getNom());
        jpa.setLieu(Conference.getLieu());
        jpa.setDate(Conference.getDate());
        jpa.setCapacite_max(Conference.getCapacite_max());
        jpa.setDescription(Conference.getDescription());
        jpa.setVisibilite(Conference.getVisibilite());
        jpa.setImage_url(Conference.getImage_url());
        // Champs spécifiques aux Conferences
        jpa.setTheme(Conference.getTheme());
        return jpa;
    }

    @Override
    public Evenement convertirVersModele(EvenementJpa jpa) {
        Conference Conference = new Conference();
        // Copie des champs communs
        Conference.setNom(jpa.getNom());
        Conference.setLieu(jpa.getLieu());
        Conference.setDate(jpa.getDate());
        Conference.setCapacite_max(jpa.getCapacite_max());
        Conference.setDescription(jpa.getDescription());
        Conference.setVisibilite(jpa.getVisibilite());
        Conference.setImage_url(jpa.getImage_url());
        // Champs spécifiques
        Conference.setTheme(((ConferenceJpa) jpa).getTheme());
        return Conference;
    }

    @Override
    public EvenementJpa enregistrer(Evenement evenement) {
        ConferenceJpa jpa = (ConferenceJpa)convertirVersJpa(evenement);
        ConferenceJpa jpaEnregistre = evenementRepository.save(jpa);
        return evenementRepository.save(jpa);
    }
}