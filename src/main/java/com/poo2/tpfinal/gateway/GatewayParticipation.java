package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.repository.ParticipationJpa;
import com.poo2.tpfinal.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

@Service
public class GatewayParticipation implements GatewayParticipationInterface {
    private ParticipationRepository participationRepository;

    @Override
    public void creerParticipation(Long id_evenement, Long id_participant, String role) {

    }

    @Override
    public String trouverRoleParticipation(Long id_evenement, Long id_participant) {
        return "";
    }

    @Override
    public void modifierRoleParticipation(Long id_evenement, Long id_participant, String role) {

    }

    @Override
    public void supprimerParticipation(Long id_evenement, Long id_participant) {

    }

    @Override
    public void saveParticipation(Long id_evenement, Long id_participant, String role) {
        if (id_participant != null) {
            ParticipationJpa participation = new ParticipationJpa();
            participation.setRole(role);

            // Créer et définir la clé composite
            ParticipationJpa.ParticipationId id = new ParticipationJpa.ParticipationId();
            id.setEvenement(id_evenement);
            id.setParticipant(id_participant);
            participationRepository.save(participation);
        }
    }
}
