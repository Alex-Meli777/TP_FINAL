package com.poo2.tpfinal.controller;

import com.poo2.tpfinal.dto.SetOrganisateurRequestDto;
import com.poo2.tpfinal.service.OrganisateurServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur pour la gestion des organisateurs
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrganisateurController {
    private final OrganisateurServiceInterface organisateurService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Crée un nouvel organisateur
     * @param setOrganisateurRequestDto DTO contenant les informations de l'organisateur
     * @return Réponse HTTP avec un message de succès ou d'erreur
     */
    @PostMapping("/organisateur/creer")
    public ResponseEntity<Map<String, String>> creerOrganisateur(
            @RequestBody SetOrganisateurRequestDto setOrganisateurRequestDto) {
        
        log.info("Tentative de création d'un organisateur: {}", setOrganisateurRequestDto.getNom_utilisateur());
        
        try {
            // Appel du service pour créer l'organisateur
            organisateurService.setOrganisateur(setOrganisateurRequestDto);
            
            // Réponse en cas de succès
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Organisateur créé avec succès");
            
            log.info("Organisateur créé avec succès: {}", setOrganisateurRequestDto.getNom_utilisateur());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            // Erreur de validation des données
            log.warn("Erreur de validation lors de la création d'un organisateur: {}", e.getMessage());
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            // Erreur inattendue
            log.error("Erreur inattendue lors de la création d'un organisateur", e);
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Une erreur est survenue lors de la création de l'organisateur");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Connecte un organisateur
     * @param setOrganisateurRequestDto DTO contenant les informations de connexion
     * @return Réponse HTTP avec un message de succès ou d'erreur
     */
    @PostMapping("/organisateur/connexion")
    public ResponseEntity<Map<String, Object>> connexionOrganisateur(
            @RequestBody SetOrganisateurRequestDto setOrganisateurRequestDto) {
        
        log.info("Tentative de connexion de l'organisateur: {}", setOrganisateurRequestDto.getNom_utilisateur());
        
        try {
            // Ici, vous devriez implémenter la logique de connexion
            // Pour l'instant, on simule une connexion réussie
            
            // Vérifier que le nom d'utilisateur et le mot de passe sont fournis
            if (setOrganisateurRequestDto.getNom_utilisateur() == null || 
                setOrganisateurRequestDto.getNom_utilisateur().trim().isEmpty() ||
                setOrganisateurRequestDto.getMot_de_passe() == null || 
                setOrganisateurRequestDto.getMot_de_passe().trim().isEmpty()) {
                
                throw new IllegalArgumentException("Le nom d'utilisateur et le mot de passe sont requis");
            }
            
            // Simuler une vérification des identifiants
            // Dans une vraie application, vous vérifieriez cela dans la base de données
            // et généreriez un token JWT
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Connexion réussie");
            response.put("token", "simulated-jwt-token"); // À remplacer par un vrai JWT
            
            log.info("Connexion réussie pour l'organisateur: {}", setOrganisateurRequestDto.getNom_utilisateur());
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            // Identifiants manquants ou invalides
            log.warn("Échec de la connexion: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            
        } catch (Exception e) {
            // Erreur inattendue
            log.error("Erreur inattendue lors de la connexion", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Une erreur est survenue lors de la connexion");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Supprime un organisateur existant
     * @param id l'identifiant de l'organisateur à supprimer
     * @return Réponse HTTP avec un message de succès ou d'erreur
     */
    @DeleteMapping("/organisateur/supprimer/{id}")
    public ResponseEntity<Map<String, String>> supprimerOrganisateur(@PathVariable Long id) {
        try {
            organisateurService.deleteOrganisateur(id);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Organisateur supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Une erreur est survenue lors de la suppression de l'organisateur");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
