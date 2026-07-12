package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "niveau",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_niveau_cycle_code", columnNames = {"cycle_id", "code"}),
        @UniqueConstraint(name = "uq_niveau_cycle_rang", columnNames = {"cycle_id", "rang_dans_cycle"})
    }
)
@Getter
@Setter
public class NiveauJpaEntity extends EntiteReferentielJpaEntity {

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_id", nullable = false)
    private CycleJpaEntity cycle;

    @Column(nullable = false, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 30)
    private String libelleCourt;

    @Column(length = 150)
    private String libelleEn;

    @Column(name = "rang_dans_cycle", nullable = false)
    private Integer rangDansCycle;

    @Column
    private Integer ageTheoriqueDebut;

    @Column(columnDefinition = "TEXT")
    private String description;

}
