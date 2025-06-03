package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.model.Organisateur;
import com.poo2.tpfinal.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatewayOrganisateur implements GatewayOrganisateurInterface {
    private final OrganisateurRepository organisateurRepository;
    private final OrganisationRepository organisationRepository;
    private final EvenementRepository evenementRepository;

    @Override
    public void creerOrganisateur(Organisateur organisateur) {
        try {
            // Vérifier si l'utilisateur existe déjà
            if (organisateurRepository.findByNomUtilisateur(organisateur.getNom_utilisateur()) != null) {
                throw new IllegalArgumentException("Un organisateur avec ce nom d'utilisateur existe déjà");
            }
            
            // Convertir le modèle en entité JPA
            OrganisateurJpa organisateurJpa = convertToJpa(organisateur);
            
            // Sauvegarder l'organisateur
            organisateurRepository.save(organisateurJpa);
        } catch (DataIntegrityViolationException e) {
            // Gérer les violations de contraintes d'intégrité (comme les doublons)
            throw new IllegalArgumentException("Erreur lors de la création de l'organisateur: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur inattendue lors de la création de l'organisateur", e);
        }
    }


    @Override
    public Organisateur getOrganisateurById(Long id) {
        Optional<OrganisateurJpa> organisateurJpa = organisateurRepository.findById(id);

        //forme condensée de isPresent()
        return organisateurJpa.map(this::convertToModel).orElse(null);
    }

    @Override
    @Transactional
    public void deleteOrganisateur(Long id) {
        // Supprimer d'abord les organisations liées
        List<OrganisationJpa> organisations = organisationRepository.findByOrganisateurId(id);
        organisationRepository.deleteAll(organisations);

        // Supprimer les événements créés par cet organisateur
        List<OrganisationJpa> createdEvents = organisationRepository.findCreatedEventsByOrganisateurId(id);
        List<Long> eventIds = createdEvents.stream()
                .map(org -> org.getEvenement().getId_ev())
                .collect(Collectors.toList());
        evenementRepository.deleteAllById(eventIds);


        organisateurRepository.deleteById(id);
    }

    /**
     * Convertit une entité JPA OrganisateurJpa en un modèle Organisateur
     */

    private Organisateur convertToModel(OrganisateurJpa organisateurJpa) {
        if (organisateurJpa == null) {
            return null;
        }
        Organisateur organisateur = new Organisateur();
        organisateur.setNom(organisateurJpa.getNom());
        organisateur.setEmail(organisateurJpa.getEmail());
        organisateur.setNom_utilisateur(organisateurJpa.getNom_utilisateur());
        organisateur.setMot_de_passe(organisateurJpa.getMot_de_passe());

        return organisateur;
    }


    /**
     * Convertit un modèle Organisateur en entité JPA OrganisateurJpa
     */
    private OrganisateurJpa convertToJpa(Organisateur organisateur) {
        if (organisateur == null) {
            return null;
        }
        
        OrganisateurJpa jpa = new OrganisateurJpa();
        jpa.setNom(organisateur.getNom());
        jpa.setEmail(organisateur.getEmail());
        jpa.setNom_utilisateur(organisateur.getNom_utilisateur());
        jpa.setMot_de_passe(organisateur.getMot_de_passe());
        
        return jpa;
    }
}
