package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "filiere",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_filiere_oe_te_code",
        columnNames = {"ordre_enseignement_id", "type_enseignement_id", "code"}
    ),
    schema = "referentiel"
)
@Getter
@Setter
public class FiliereJpaEntity extends EntiteReferentielJpaEntity {

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordre_enseignement_id", nullable = false)
    private OrdreEnseignementJpaEntity ordreEnseignement;

    // ON DELETE RESTRICT en base
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_enseignement_id", nullable = false)
    private TypeEnseignementJpaEntity typeEnseignement;

    @Column(nullable = false, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 150)
    private String libelleEn;

    @Column(columnDefinition = "TEXT")
    private String description;

}
