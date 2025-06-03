package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.repository.EvenementRepository;
import com.poo2.tpfinal.repository.OrganisationJpa;
import com.poo2.tpfinal.repository.OrganisationRepository;
import com.poo2.tpfinal.repository.OrganisateurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GatewayOrganisation implements GatewayOrganisationInterface {
    private final OrganisationRepository organisationRepository;
    private final EvenementRepository evenementRepository;
    private final OrganisateurRepository organisateurRepository;

    @Override
    @Transactional
    public void creerOrganisation(Long idEvenement, Long idOrganisateur, boolean createur) {
        // Vérifier si la relation existe déjà
        if (organisationRepository.existsByOrganisateurIdAndEvenementId(idOrganisateur, idEvenement)) {
            throw new IllegalArgumentException("Cet organisateur est déjà associé à cet événement");
        }

        // Récupérer les entités
        var evenement = evenementRepository.findById(idEvenement)
                .orElseThrow(() -> new IllegalArgumentException("Événement non trouvé"));

        var organisateur = organisateurRepository.findById(idOrganisateur)
                .orElseThrow(() -> new IllegalArgumentException("Organisateur non trouvé"));

        // Créer la nouvelle organisation
        var organisation = new OrganisationJpa();
        organisation.setEvenement(evenement);
        organisation.setOrganisateur(organisateur);
        organisation.setCreateur(createur);

        // Initialiser l'ID
        var id = new OrganisationJpa.OrganisationId();
        id.setOrganisateurId(idOrganisateur);
        id.setEvenementId(idEvenement);
        organisation.setId(id);

        // Sauvegarder
        organisationRepository.save(organisation);
    }


    @Override
    public boolean trouverSiCreateur(Long id_evenement, Long id_createur) {
        Optional<OrganisationJpa> organisation = organisationRepository.findByOrganisateurIdAndEvenementId(id_createur,id_evenement);
        //Equivaut à isPresent()
        return organisation.map(OrganisationJpa::isCreateur).orElse(false);
    }

    @Override
    public boolean trouverSiOrganisateur(Long id_evenement, Long id_organisateur) {
        Optional<OrganisationJpa> organisation = organisationRepository.findByOrganisateurIdAndEvenementId(id_organisateur,id_evenement);
        return organisation.isPresent();
    }

    @Override
    public void supprimerOrganisation(Long id_evenement, Long id_organisateur) {
        Optional<OrganisationJpa> organisation = organisationRepository.findByOrganisateurIdAndEvenementId(id_organisateur,id_evenement);
        if (organisation.isPresent()) {
            organisationRepository.delete(organisation.get());
        }else {
            throw new IllegalArgumentException("L'organisateur n'est pas organisateur de cet evenement");
        }
    }
}
