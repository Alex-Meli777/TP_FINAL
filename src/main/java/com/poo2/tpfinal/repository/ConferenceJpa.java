package com.poo2.tpfinal.repository;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CONFERENCE")
public class ConferenceJpa extends EvenementJpa{
    private String theme;
}
