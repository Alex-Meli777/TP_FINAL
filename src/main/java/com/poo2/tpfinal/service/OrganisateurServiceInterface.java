package com.poo2.tpfinal.service;

import com.poo2.tpfinal.dto.SetOrganisateurRequestDto;
import com.poo2.tpfinal.model.Organisateur;

public interface OrganisateurServiceInterface {
    //public void getOrganisateur(Long id);
    public void setOrganisateur(SetOrganisateurRequestDto setOrganisateurRequestDto);

    Organisateur getOrganisateurById(Long id);

    void deleteOrganisateur(Long id);
}
