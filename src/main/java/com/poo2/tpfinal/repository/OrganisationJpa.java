package com.poo2.tpfinal.repository;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "organisation")
public class OrganisationJpa {
    @EmbeddedId
    private OrganisationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organisateurId")
    @JoinColumn(name = "organisateur_id", referencedColumnName = "id_par")
    private OrganisateurJpa organisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("evenementId")
    @JoinColumn(name = "evenement_id", referencedColumnName = "id_ev")
    private EvenementJpa evenement;

    private boolean createur;

    @Data
    @Embeddable
    public static class OrganisationId implements Serializable {
        private Long organisateurId;
        private Long evenementId;
    }
}