package com.poo2.tpfinal.dto;

import com.poo2.tpfinal.model.TypeEvenement;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class SetEvenementDto {
    private Map<String, Object> attributs;  // Changé de String à Object

    // Méthodes utilitaires pour accéder aux champs courants
    public String getType() {
        return (String) attributs.get("type");
    }

    public String getNom() {
        return (String) attributs.get("nom");
    }

    public String getLieu() {
        return (String) attributs.get("lieu");
    }

    public LocalDate getDate() {
        return LocalDate.parse((String) attributs.get("date"));
    }

    public Integer getCapaciteMax() {
        if (attributs.get("capacite_max") instanceof String) {
            return Integer.parseInt((String) attributs.get("capacite_max"));
        }
        return (Integer) attributs.get("capacite_max");
    }

    public String getDescription() {
        return (String) attributs.get("description");
    }

    public String getVisibilite() {
        return (String) attributs.get("visibilite");
    }

    public String getImageUrl() {
        return (String) attributs.get("image_url");
    }

    // Méthodes spécifiques aux types d'événements
    public String getGenre() {
        return (String) attributs.get("genre");
    }

    public String getTheme() {
        return (String) attributs.get("theme");
    }

    public Long getOrganisateurId() {
        Object orgId = attributs.get("organisateurId");
        if (orgId instanceof String) {
            return Long.parseLong((String) orgId);
        } else if (orgId instanceof Integer) {
            return ((Integer) orgId).longValue();
        }
        return (Long) orgId;
    }
}