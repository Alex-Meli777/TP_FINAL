package com.poo2.tpfinal.util;

import com.poo2.tpfinal.model.Concert;
import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.model.TypeEvenement;
import com.poo2.tpfinal.repository.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConcertGatewayStrategy implements EvenementGatewayStrategy<ConcertJpa, Concert> {
    private final EvenementRepository evenementRepository;
    private final ParticipationRepository participationRepository;

    @Autowired
    public ConcertGatewayStrategy(EvenementRepository evenementRepository, ParticipationRepository participationRepository) {
        this.evenementRepository = evenementRepository;
        this.participationRepository = participationRepository;
    }
    @Override
    public boolean peutGerer(TypeEvenement type) {
        return TypeEvenement.CONCERT == type;
    }

    @Override
    public Class<ConcertJpa> getTypeJpa() {
        return ConcertJpa.class;
    }

    @Override
    public EvenementJpa convertirVersJpa(Evenement evenement) {
        Concert concert = (Concert) evenement;
        ConcertJpa jpa = new ConcertJpa();
        // Copie des champs communs
        jpa.setNom(concert.getNom());
        jpa.setLieu(concert.getLieu());
        jpa.setDate(concert.getDate());
        jpa.setCapacite_max(concert.getCapacite_max());
        jpa.setDescription(concert.getDescription());
        jpa.setVisibilite(concert.getVisibilite());
        jpa.setImage_url(concert.getImage_url());
        // Champs spécifiques aux concerts
        jpa.setGenre(concert.getGenre());
        return jpa;
    }

    @Override
    public Evenement convertirVersModele(EvenementJpa jpa) {
        Concert concert = new Concert();
        // Copie des champs communs
        concert.setNom(jpa.getNom());
        concert.setLieu(jpa.getLieu());
        concert.setDate(jpa.getDate());
        concert.setCapacite_max(jpa.getCapacite_max());
        concert.setDescription(jpa.getDescription());
        concert.setVisibilite(jpa.getVisibilite());
        concert.setImage_url(jpa.getImage_url());
        // Champs spécifiques
        concert.setGenre(((ConcertJpa) jpa).getGenre());
        return concert;
    }

    @Override
    public EvenementJpa enregistrer(Evenement evenement) {
        ConcertJpa jpa = (ConcertJpa)convertirVersJpa(evenement);
        return evenementRepository.save(jpa);

    }
}