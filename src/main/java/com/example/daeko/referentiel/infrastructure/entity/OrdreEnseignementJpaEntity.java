package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "ordre_enseignement",
    uniqueConstraints = @UniqueConstraint(name = "uq_ordre_enseignement_code", columnNames = "code"),
    schema = "referentiel"
)
@Getter
@Setter
public class OrdreEnseignementJpaEntity extends EntiteReferentielJpaEntity {

    @Column(nullable = false, length = 30, unique = true)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 150)
    private String tutelleMinisterielle;

    @Column(length = 150)
    private String tutelleMinisterielleEn;

    @Column(nullable = false)
    private Integer rang;

    @Column(columnDefinition = "TEXT")
    private String description;

}
