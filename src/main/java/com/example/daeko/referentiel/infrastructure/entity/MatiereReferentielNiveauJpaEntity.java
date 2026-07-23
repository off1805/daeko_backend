package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 *  Unicité partielle
 *   - si serie_id IS NOT NULL : UNIQUE (matiere_referentiel_id, niveau_id, serie_id)
 *   - si serie_id IS NULL     : UNIQUE (matiere_referentiel_id, niveau_id)
 */
@Entity
@Table(name = "matiere_referentiel_niveau", schema = "referentiel")
@Getter
@Setter
public class MatiereReferentielNiveauJpaEntity extends EntiteReferentielJpaEntity {

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_referentiel_id", nullable = false)
    private MatiereReferentielJpaEntity matiereReferentiel;

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_id", nullable = false)
    private NiveauJpaEntity niveau;

    // ON DELETE RESTRICT en base — nullable : NULL signifie "toutes séries"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id")
    private SerieJpaEntity serie;

    @Column(nullable = false)
    private boolean estObligatoire = true;

    @Column(precision = 4, scale = 1)
    private BigDecimal coefficientSuggere;

    @Column(columnDefinition = "TEXT")
    private String sourceCoefficient;

    @Column
    private Integer baremeSpecifique;

    @Column(columnDefinition = "TEXT")
    private String description;

}
