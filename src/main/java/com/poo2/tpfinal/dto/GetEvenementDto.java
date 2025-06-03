package com.poo2.tpfinal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class GetEvenementDto {
    private Map<String, String> attributs; // attributs de l'evenement

    public void setCapaciteMax(Integer capaciteMax) {
        attributs.put("capacite_max", capaciteMax.toString());
    }

    public void setVisibilite(String visibilite) {
        attributs.put("visibilite", visibilite);
    }

    public void setImageUrl(String imageUrl) {
        attributs.put("image_url", imageUrl);
    }

    public void setNom(String nom) {
        attributs.put("nom", nom);
    }

    public void setLieu(String lieu) {
        attributs.put("lieu", lieu);
    }

    public void setDate(LocalDate date) {
        attributs.put("date", date.toString());
    }

    public void setDescription(String description) {
        attributs.put("description", description);
    }

    public void setType(String type) {
        attributs.put("type", type);
    }
    //Spécifiques
    public void setTheme(String theme) {
        attributs.put("theme", theme);
    }

    public void setGenre(String genre) {
        attributs.put("genre", genre);
    }
}