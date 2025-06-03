package com.poo2.tpfinal.gateway;

public interface GatewayOrganisationInterface {
    //CR_D, car on ne modifie rien ici (on ne modifie pas le createur
    public void creerOrganisation(Long id_evenement, Long id_organisateur,boolean createur);
    public boolean trouverSiCreateur(Long id_evenement, Long id_createur);
    public boolean trouverSiOrganisateur(Long id_evenement, Long id_organisateur);
    public void supprimerOrganisation(Long id_evenement, Long id_participant);
}
