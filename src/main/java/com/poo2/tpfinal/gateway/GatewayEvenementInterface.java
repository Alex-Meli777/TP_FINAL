package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.model.Evenement;

public interface GatewayEvenementInterface {
    void supprimerEvenement(Long id);

    Long sauvegarder(Evenement nouvelEvenement);

    Evenement getEvenement(Long id);

}
