package com.poo2.tpfinal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Conference extends Evenement {
    private String theme;

    @Override
    public TypeEvenement getType() {
        return TypeEvenement.CONFERENCE;
    }
}
