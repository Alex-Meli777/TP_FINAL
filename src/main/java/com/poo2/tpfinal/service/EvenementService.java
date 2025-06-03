package com.poo2.tpfinal.service;

import com.poo2.tpfinal.dto.GetEvenementDto;
import com.poo2.tpfinal.dto.SetEvenementDto;
import com.poo2.tpfinal.gateway.GatewayOrganisationInterface;
import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.gateway.GatewayEvenementInterface;
import com.poo2.tpfinal.util.EvenementFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Data
@Service
    public class EvenementService implements EvenementServiceInterface {
        private GatewayEvenementInterface gatewayEvenement;
        private final GatewayOrganisationInterface gatewayOrganisation;
        private final List<EvenementFactory> factories;

        @Autowired
        public EvenementService(GatewayEvenementInterface gatewayEvenement, GatewayOrganisationInterface gatewayOrganisation,
                                List<EvenementFactory> factories) {
        this.gatewayEvenement = gatewayEvenement;
            this.gatewayOrganisation = gatewayOrganisation;
            this.factories = factories;
    }

        @Override
        public Evenement setEvenement(SetEvenementDto dto) {
            String typeEvenement = dto.getType();

            Evenement nouvelEvenement = factories.stream()
                    .filter(f -> f.supporteType(typeEvenement))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Type d'événement non supporté: " + typeEvenement))
                    .creerEventByFactory(dto);

            Long id_evenement = gatewayEvenement.sauvegarder(nouvelEvenement);
            //Sauvegarder aussi le createur
            gatewayOrganisation.creerOrganisation(id_evenement, dto.getOrganisateurId(), true);
            return nouvelEvenement;
        }

    @Override
    public void deleteEvenement(Long evenementId) {
            gatewayEvenement.supprimerEvenement(evenementId);
    }

    @Override
    public void ajouterOrganisateur(Long evenementId, Long organisateurId) {
        gatewayOrganisation.creerOrganisation(evenementId, organisateurId, false);
    }

    @Override
    public void supprimerOrganisateur(Long evenementId, Long organisateurId) {
        gatewayOrganisation.supprimerOrganisation(evenementId, organisateurId);
    }

    @Override
    public GetEvenementDto getEvenement(Long evenementId) {
        Evenement evenement = gatewayEvenement.getEvenement(evenementId);
        if (evenement == null) {
            throw new NoSuchElementException("Événement non trouvé avec l'ID: " + evenementId);
        }
        return convertModelToDto(evenement);
    }
    
    public GetEvenementDto convertModelToDto(Evenement evenement) {
        Map<String, String> resultMap = new HashMap<>();
        if (evenement == null) {
            return null;
        }

        Class<?> classe = evenement.getClass();

        // Parcourir tous les champs de la classe et de ses superclasses
        while (classe != null && classe != Object.class) {
            for (Field field : classe.getDeclaredFields()) {
                // Ignorer les champs statiques
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                try {
                    Object value = field.get(evenement);
                    String stringValue = convertAnyToString(value);
                    resultMap.put(field.getName(), stringValue);
                } catch (IllegalAccessException e) {
                    // Ignorer les champs inaccessibles
                    System.err.println("Impossible d'accéder au champ " + field.getName() + ": " + e.getMessage());
                }
            }
            classe = classe.getSuperclass();
        }

        GetEvenementDto dto = new GetEvenementDto();
        dto.setAttributs(resultMap);
        return dto;
    }

    private String convertAnyToString(Object value) {
        if (value == null) {
            return null;
        }

        // Gestion des types temporels
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else if (value instanceof LocalDate) {
            return ((LocalDate) value).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        // Gestion des nombres
        else if (value instanceof Number) {
            if (value instanceof Double || value instanceof Float) {
                // Évite la notation scientifique pour les nombres à virgule
                return new BigDecimal(value.toString()).stripTrailingZeros().toPlainString();
            }
            return value.toString();
        }
        // Gestion des booléens
        else if (value instanceof Boolean) {
            return value.toString();
        }
        // Gestion des tableaux et collections
        else if (value.getClass().isArray()) {
            return Arrays.deepToString((Object[]) value);
        } else if (value instanceof Collection) {
            return value.toString();
        }
        // Pour tout autre type, utiliser toString()
        return value.toString();
    }
}
