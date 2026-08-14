package com.example.daeko.structure_pedagogique.infrastructure.entity;

import com.example.daeko.structure_pedagogique.domain.model.EtatBranche;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "branche", schema = "structure_pedagogique",
    uniqueConstraints = @UniqueConstraint(name = "uq_branche_triplet",
        columnNames = {"etablissement_id", "sous_systeme_id", "ordre_enseignement_id", "type_enseignement_id"}))
public class BrancheJpaEntity extends AuditableJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "etablissement_id", nullable = false)
    private UUID etablissementId;

    @Column(name = "sous_systeme_id", nullable = false)
    private UUID sousSystemeId;

    @Column(name = "ordre_enseignement_id", nullable = false)
    private UUID ordreEnseignementId;

    @Column(name = "type_enseignement_id", nullable = false)
    private UUID typeEnseignementId;

    @Column(name = "libelle", nullable = false, length = 200)
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false, columnDefinition = "structure_pedagogique.etat_branche")
    private EtatBranche etat;
}
