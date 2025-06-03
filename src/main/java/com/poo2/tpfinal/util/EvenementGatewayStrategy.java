package com.poo2.tpfinal.util;

import com.poo2.tpfinal.model.Evenement;
import com.poo2.tpfinal.model.TypeEvenement;
import com.poo2.tpfinal.repository.EvenementJpa;

public interface EvenementGatewayStrategy<T extends EvenementJpa,E extends Evenement> {
        boolean peutGerer(TypeEvenement type);

        Class <T> getTypeJpa();

        default boolean peuTransformer(EvenementJpa jpa){
                return jpa!= null && getTypeJpa().isInstance(jpa);
        }

        EvenementJpa convertirVersJpa(Evenement evenement);
        Evenement convertirVersModele(EvenementJpa jpa);
        public EvenementJpa enregistrer(Evenement evenement);
}
