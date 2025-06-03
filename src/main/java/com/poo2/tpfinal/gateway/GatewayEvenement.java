package com.poo2.tpfinal.gateway;

import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.repository.EvenementJpa;
import com.poo2.tpfinal.repository.EvenementRepository;
import com.poo2.tpfinal.repository.ParticipationRepository;
import com.poo2.tpfinal.util.EvenementGatewayStrategy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class GatewayEvenement implements GatewayEvenementInterface {
    private final EvenementRepository evenementRepository;
    private final ParticipationRepository participationRepository;
    private  EvenementGatewayStrategy<EvenementJpa, Evenement> evenementGatewayStrategy;
    private final List<EvenementGatewayStrategy<? extends EvenementJpa, ? extends Evenement>> strategies;

    @Autowired
    public GatewayEvenement(EvenementRepository evenementRepository, ParticipationRepository participationRepository,
                            List<EvenementGatewayStrategy<? extends EvenementJpa, ? extends Evenement>> strategies) {
        this.evenementRepository = evenementRepository;
        this.participationRepository = participationRepository;
        this.strategies = strategies;
        System.out.println("=== Initialisation GatewayEvenement ===");
        System.out.println("Stratégies chargées: " + strategies.size());
        strategies.forEach(s ->
                System.out.println(" - " + s.getClass().getSimpleName() + " gère: " +
                        (s.peutGerer(com.poo2.tpfinal.model.TypeEvenement.CONCERT) ? "CONCERT " : "") +
                        (s.peutGerer(com.poo2.tpfinal.model.TypeEvenement.CONFERENCE) ? "CONFERENCE" : ""))
        );
    }

    @SuppressWarnings("unchecked")
    public Long sauvegarder(Evenement evenement) {
        System.out.println("\n=== Début sauvegarde ===");
        System.out.println("Classe de l'événement: " + evenement.getClass().getName());
        System.out.println("Type d'événement reçu: " + evenement.getType());

        // Afficher toutes les stratégies disponibles
        System.out.println("Stratégies disponibles (" + strategies.size() + "):");
        strategies.forEach(strat -> {
            System.out.println(" - " + strat.getClass().getSimpleName() +
                    " gère: " + strat.peutGerer(com.poo2.tpfinal.model.TypeEvenement.CONCERT) + " (CONCERT), " +
                    strat.peutGerer(com.poo2.tpfinal.model.TypeEvenement.CONFERENCE) + " (CONFERENCE)");
        });

        try {
            EvenementGatewayStrategy<? extends EvenementJpa, ? extends Evenement> strategy = strategies.stream()
                    .filter(s -> s.peutGerer(evenement.getType()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Aucune stratégie ne peut gérer le type: " + evenement.getType()));

            System.out.println("Stratégie sélectionnée: " + strategy.getClass().getSimpleName());

            // Utilisation d'un cast non sécurisé, mais nécessaire ici
            EvenementGatewayStrategy<EvenementJpa, Evenement> typedStrategy =
                    (EvenementGatewayStrategy<EvenementJpa, Evenement>) strategy;

            Long id = typedStrategy.enregistrer(evenement).getId_ev();
            System.out.println("=== Fin sauvegarde avec succès, avec un id d'évènement"+id+" ===\n");

            return id;
        } catch (Exception e) {
            System.err.println("\n=== ERREUR lors de la sauvegarde ===");
            System.err.println("Type d'événement: " + evenement.getType());
            System.err.println("Message d'erreur: " + e.getMessage());
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Evenement getEvenement(Long id) {
        EvenementJpa evenementJpa = evenementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Événement non trouvé"));

        return strategies.stream()
                .filter(strategy -> strategy.peuTransformer(evenementJpa))
                .findFirst()
                .map(strategy -> convertirAvecTypeSecurise(strategy, evenementJpa))
                .orElseThrow(() -> new IllegalStateException("Aucune stratégie pour le type: " + evenementJpa.getClass().getSimpleName()));
    }

    @SuppressWarnings("unchecked")
    private <JPA extends EvenementJpa, E extends Evenement> E convertirAvecTypeSecurise(
            EvenementGatewayStrategy<JPA, E> strategy, EvenementJpa jpa) {
        Class<JPA> jpaClass = strategy.getTypeJpa();
        if (!jpaClass.isInstance(jpa)) {
            throw new IllegalStateException("Type JPA inattendu: " + jpa.getClass().getName());
        }
        JPA typedJpa = jpaClass.cast(jpa);
        Evenement modele = strategy.convertirVersModele(typedJpa);
        return (E) modele;
    }

    @Override
    public void supprimerEvenement(Long id) {
        evenementRepository.deleteById(id);
    }
}