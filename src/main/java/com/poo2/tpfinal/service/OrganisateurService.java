package com.poo2.tpfinal.service;

import com.poo2.tpfinal.dto.SetOrganisateurRequestDto;
import com.poo2.tpfinal.gateway.GatewayOrganisateurInterface;
import com.poo2.tpfinal.model.Organisateur;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service pour la gestion des organisateurs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganisateurService implements OrganisateurServiceInterface {
    private final GatewayOrganisateurInterface gatewayInterface;

    /**
     * Crée un nouvel organisateur
     * @param setOrganisateurRequestDto DTO contenant les informations de l'organisateur
     * @throws IllegalArgumentException si les données sont invalides ou si l'organisateur existe déjà
     * @throws RuntimeException en cas d'erreur inattendue
     */
    @Override
    public void setOrganisateur(SetOrganisateurRequestDto setOrganisateurRequestDto) {
        try {
            // Valider les données d'entrée
            validateOrganisateurData(setOrganisateurRequestDto);
            
            // Convertir le DTO en modèle
            Organisateur organisateur = dtoToModel(setOrganisateurRequestDto);
            
            // Déléguer la création au gateway
            gatewayInterface.creerOrganisateur(organisateur);
            
            log.info("Organisateur créé avec succès: {}", setOrganisateurRequestDto.getNom_utilisateur());
            
        } catch (IllegalArgumentException e) {
            // Relancer les erreurs de validation telles quelles
            throw e;
        } catch (Exception e) {
            // Logger l'erreur complète pour le débogage
            log.error("Erreur lors de la création de l'organisateur: {}", e.getMessage(), e);
            throw new RuntimeException("Une erreur est survenue lors de la création de l'organisateur", e);
        }
    }


    /**
     * Récupère un organisateur par son ID.
     *
     * @param id l'identifiant de l'organisateur à récupérer
     * @return l'organisateur correspondant à l'ID fourni
     * @throws IllegalArgumentException si l'organisateur avec l'ID donné n'existe pas
     */
    @Override
    public Organisateur getOrganisateurById(Long id) {
        Organisateur organisateur = gatewayInterface.getOrganisateurById(id);
        if (organisateur == null) {
            throw new IllegalArgumentException("L'organisateur avec l'ID " + id + " n'existe pas");
        }
        return organisateur;
    }

    @Override
    public void deleteOrganisateur(Long id) {
        gatewayInterface.deleteOrganisateur(id);
    }

    /**
     * Valide les données de l'organisateur
     */
    private void validateOrganisateurData(SetOrganisateurRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Les données de l'organisateur sont requises");
        }
        
        if (dto.getNom_utilisateur() == null || dto.getNom_utilisateur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est requis");
        }
        
        if (dto.getNom() == null || dto.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est requis");
        }
        
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email est requis");
        }
        
        if (dto.getMot_de_passe() == null || dto.getMot_de_passe().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est requis");
        }
        
        // Validation simple de l'email
        if (!dto.getEmail().contains("@")) {
            throw new IllegalArgumentException("L'email n'est pas valide");
        }
    }
    
    /**
     * Convertit un DTO en modèle de domaine
     */
    private Organisateur dtoToModel(SetOrganisateurRequestDto dto) {
        if (dto == null) {
            return null;
        }
        
        return new Organisateur(
            dto.getNom_utilisateur(), 
            dto.getNom(), 
            dto.getEmail(), 
            dto.getMot_de_passe()
        );
    }
}
