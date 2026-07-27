package com.example.daeko.structure_pedagogique.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "matiere_active", schema = "structure_pedagogique")
public class MatiereActiveJpaEntity extends AuditableJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "niveau_active_id", nullable = false)
    private UUID niveauActiveId;

    @Column(name = "serie_active_id")
    private UUID serieActiveId;

    @Column(name = "matiere_referentiel_id")
    private UUID matiereReferentielId;

    @Column(name = "matiere_locale_id")
    private UUID matiereLocaleId;

    @Column(name = "coefficient", nullable = false, precision = 4, scale = 1)
    private BigDecimal coefficient;

    @Column(name = "bareme")
    private Short bareme;

    @Column(name = "est_obligatoire", nullable = false)
    private boolean estObligatoire;
}
