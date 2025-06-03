package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.model.Organisateur;

public interface GatewayOrganisateurInterface {
    void creerOrganisateur(Organisateur organisateur);

    Organisateur getOrganisateurById(Long id);

    void deleteOrganisateur(Long id);
}
