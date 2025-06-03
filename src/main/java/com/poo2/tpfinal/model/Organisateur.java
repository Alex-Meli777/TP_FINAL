package com.poo2.tpfinal.model;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Organisateur {
    @NonNull
    private String nom_utilisateur;
    @NonNull
    private String nom;
    @NonNull
    private String email;
    @NonNull
    private String mot_de_passe;

    private Map<Evenement, String> organisations;
}
