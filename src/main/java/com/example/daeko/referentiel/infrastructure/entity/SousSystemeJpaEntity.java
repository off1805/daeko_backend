package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Contrainte CHECK cycle de vie :
 *   (etat = 'ACTIVE' AND date_depreciation IS NULL)
 *   OR (etat = 'DEPRECATED' AND date_depreciation IS NOT NULL AND motif_depreciation IS NOT NULL)
 */
@Entity
@Table(
    name = "sous_systeme",
    uniqueConstraints = @UniqueConstraint(name = "uq_sous_systeme_code", columnNames = "code"),
    schema = "referentiel"
)
@Getter
@Setter
public class SousSystemeJpaEntity extends EntiteReferentielJpaEntity {

    @Column(nullable = false, length = 20, unique = true)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 50)
    private String libelleCourt;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 2)
    private String languePrincipale;

}
