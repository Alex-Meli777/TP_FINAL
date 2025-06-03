package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.repository.EvenementJpa;
import com.poo2.tpfinal.repository.ParticipationJpa;

public interface GatewayParticipationInterface {

    //CRUD
    public void creerParticipation(Long id_evenement, Long id_participant, String role);
    public String trouverRoleParticipation(Long id_evenement, Long id_participant);
    public void modifierRoleParticipation(Long id_evenement, Long id_participant, String role);
    public void supprimerParticipation(Long id_evenement, Long id_participant);


/**
 * Cr e une nouvelle participation entre un  v nement et un participant, pour sauvegarder en BD.
 * @param id_evenement l'ID de l'évènement
 * @param id_participant l'ID du participant
 * @param role le rôle du participant (par exemple, "organisateur" ou "participant")
 */

    void saveParticipation(Long id_evenement, Long id_participant, String role);
}
