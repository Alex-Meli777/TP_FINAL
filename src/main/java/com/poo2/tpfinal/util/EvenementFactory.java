package com.poo2.tpfinal.util;

import com.poo2.tpfinal.model.TypeEvenement;
import com.poo2.tpfinal.dto.SetEvenementDto;
import com.poo2.tpfinal.model.Evenement;

public interface EvenementFactory {
    public Evenement creerEventByFactory(SetEvenementDto dto);
    boolean supporteType(String type);
}
