package com.poo2.tpfinal.util;

import com.poo2.tpfinal.dto.SetEvenementDto;
import com.poo2.tpfinal.model.Concert;
import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.model.TypeEvenement;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConcertFactory implements EvenementFactory {
    @Override
    public Evenement creerEventByFactory(SetEvenementDto dto) {
        Concert concert = new Concert();
        // Mappez les champs du DTO vers le modèle Concert
        concert.setNom((dto.getNom()));
        concert.setLieu(dto.getLieu());
        concert.setDate(dto.getDate());
        concert.setCapacite_max(dto.getCapaciteMax());
        concert.setDescription(dto.getDescription());
        concert.setVisibilite(dto.getVisibilite());
        concert.setGenre(dto.getGenre());
        concert.setImage_url(dto.getImageUrl());
        return concert;
    }

    @Override
    public boolean supporteType(String type) {
        return "CONCERT".equalsIgnoreCase(type);
    }
}