package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "matiere_referentiel",
    uniqueConstraints = @UniqueConstraint(name = "uq_matiere_ref_ss_code", columnNames = {"sous_systeme_id", "code"}),
    schema = "referentiel"
)
@Getter
@Setter
public class MatiereReferentielJpaEntity extends EntiteReferentielJpaEntity {

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_systeme_id", nullable = false)
    private SousSystemeJpaEntity sousSysteme;

    @Column(nullable = false, length = 30)
    private String code;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(length = 30)
    private String libelleCourt;

    @Column(length = 200)
    private String libelleEn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DomaineMatiereJpa domaine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeMatiereReferentielJpa typeMatiere;

    @Column(nullable = false)
    private Integer baremeParDefaut = 20;

    @Column(columnDefinition = "TEXT")
    private String description;

}
