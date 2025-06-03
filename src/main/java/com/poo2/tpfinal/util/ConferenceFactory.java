package com.poo2.tpfinal.util;

import com.poo2.tpfinal.dto.SetEvenementDto;
import com.poo2.tpfinal.model.Conference;
import com.poo2.tpfinal.model.Evenement;
import org.springframework.stereotype.Component;

@Component
public class ConferenceFactory implements EvenementFactory {
    @Override
    public Evenement creerEventByFactory(SetEvenementDto dto) {
        Conference conference = new Conference();
        conference.setNom(dto.getNom());
        conference.setLieu(dto.getLieu());
        conference.setDate(dto.getDate());
        conference.setCapacite_max(dto.getCapaciteMax());
        conference.setDescription(dto.getDescription());
        conference.setVisibilite(dto.getVisibilite());
        conference.setImage_url(dto.getImageUrl());
        conference.setTheme(dto.getTheme());
        return conference;
    }

    @Override
    public boolean supporteType(String type) {
        return type != null && type.equalsIgnoreCase("CONFERENCE");
    }
}
