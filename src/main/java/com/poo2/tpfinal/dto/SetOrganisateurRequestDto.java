package com.poo2.tpfinal.dto;

import lombok.Data;

/*  La DTO permet de faire le passage entre le controller et le service
* Ici, on va gérer la création d'un organisateur, et sa connexion
* Pour la création, tous les champs seront remplis.
* Pour la connexion, seulement le nom_utilisateur et le mot_de_passe seront remplis
*/

@Data
public class SetOrganisateurRequestDto {
    private String nom_utilisateur;
    private String nom;
    private String email;
    private String mot_de_passe;
}
