package com.poo2.tpfinal.controller;

import com.poo2.tpfinal.dto.GetEvenementDto;
import com.poo2.tpfinal.dto.SetEvenementDto;
import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.service.EvenementService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/evenement")
@Data
public class EvenementController {
    private final EvenementService evenementService;

/*
* Format de reception:
* (("type",<concert/conference>)
*   ("nom", <nom de l'evenement>)
*   ("lieu", <lieu de l'evenement>)
*   ("date", <date de l'evenement>)
*   ("capacite_max", <capacite maximale de l'evenement>)
*   ("description", <description de l'evenement>)
*   ("visibilite", <visibilite de l'evenement>)
*   ("image_url", <url de l'image de l'evenement>)
*   ("genre/theme", <genre du concert/theme de la conference>)
*   ("organisateurId", <id de l'organisateur>))
*  */
public EvenementController(EvenementService evenementService) {
    this.evenementService = evenementService;
}

    @PostMapping("/creerEvenement")
    public ResponseEntity<?> creerEvenement(@RequestBody SetEvenementDto dto) {
        try {
            Evenement nouvelEvenement = evenementService.setEvenement(dto);
            return ResponseEntity.ok(nouvelEvenement);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/getEvenement")
    public ResponseEntity<?> getEvenement(@RequestParam("evenementId") Long evenementId) {
        try {
            GetEvenementDto dto = evenementService.getEvenement(evenementId);
            return ResponseEntity.ok(dto.getAttributs()); // Retourne directement la Map
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération de l'événement: " + e.getMessage());
        }
    }

    @PutMapping("/updateEvenement")
    public ResponseEntity<?> updateEvenement(@RequestBody SetEvenementDto dto) {
        try {
            Evenement evenementModifie = evenementService.setEvenement(dto);
            return ResponseEntity.ok(evenementModifie);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteEvenement")
    public ResponseEntity<?> deleteEvenement(@RequestParam("evenementId") Long evenementId) {
        try {
            evenementService.deleteEvenement(evenementId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PostMapping("/ajouterOrganisateur")
    public ResponseEntity<?> ajouterOrganisateur(@RequestParam("evenementId") Long evenementId, @RequestParam("organisateurId") Long organisateurId) {
        try {
            evenementService.ajouterOrganisateur(evenementId, organisateurId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/supprimerOrganisateur")
    public ResponseEntity<?> supprimerOrganisateur(@RequestParam("evenementId") Long evenementId, @RequestParam("organisateurId") Long organisateurId) {
        try {
            evenementService.supprimerOrganisateur(evenementId, organisateurId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}