package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "cycle",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_cycle_ss_oe_code", columnNames = {"sous_systeme_id", "ordre_enseignement_id", "code"}),
        @UniqueConstraint(name = "uq_cycle_ss_oe_rang", columnNames = {"sous_systeme_id", "ordre_enseignement_id", "rang"})
    }
)
@Getter
@Setter
public class CycleJpaEntity extends EntiteReferentielJpaEntity {

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_systeme_id", nullable = false)
    private SousSystemeJpaEntity sousSysteme;

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordre_enseignement_id", nullable = false)
    private OrdreEnseignementJpaEntity ordreEnseignement;

    @Column(nullable = false, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 150)
    private String libelleEn;

    @Column(nullable = false)
    private Integer rang;

    @Column(nullable = false)
    private Integer dureeTheoriqueAnnees;

    @Column(columnDefinition = "TEXT")
    private String description;

}
