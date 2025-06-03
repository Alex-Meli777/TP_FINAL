package com.poo2.tpfinal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Concert extends Evenement{
    private String genre;

    @Override
    public TypeEvenement getType() {
        return TypeEvenement.CONCERT;
    }
}
