package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "serie",
    uniqueConstraints = @UniqueConstraint(name = "uq_serie_filiere_code", columnNames = {"filiere_id", "code"}),
    schema = "referentiel"
)
@Getter
@Setter
public class SerieJpaEntity extends EntiteReferentielJpaEntity {

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id", nullable = false)
    private FiliereJpaEntity filiere;

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_apparition_id", nullable = false)
    private NiveauJpaEntity niveauApparition;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(length = 30)
    private String libelleCourt;

    @Column(length = 200)
    private String libelleEn;

    @Column(columnDefinition = "TEXT")
    private String description;

}
