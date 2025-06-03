package com.poo2.tpfinal.service;

import com.poo2.tpfinal.dto.GetEvenementDto;
import com.poo2.tpfinal.dto.SetEvenementDto;
import com.poo2.tpfinal.model.Evenement;

/*
* Le service convertit un DTO en modèle de domaine
*/
public interface EvenementServiceInterface {
    public Evenement setEvenement(SetEvenementDto setEvenementDto);

    void deleteEvenement(Long evenementId);

    void ajouterOrganisateur(Long evenementId, Long organisateurId);

    void supprimerOrganisateur(Long evenementId, Long organisateurId);

    GetEvenementDto getEvenement(Long evenementId);
}
