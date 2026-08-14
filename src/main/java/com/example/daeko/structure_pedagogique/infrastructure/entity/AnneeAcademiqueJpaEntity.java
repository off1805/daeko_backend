package com.example.daeko.structure_pedagogique.infrastructure.entity;

import com.example.daeko.structure_pedagogique.domain.model.EtatAnnee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "annee_academique", schema = "structure_pedagogique",
    uniqueConstraints = @UniqueConstraint(name = "uq_annee_libelle", columnNames = {"etablissement_id", "libelle"}))
public class AnneeAcademiqueJpaEntity extends AuditableJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "etablissement_id", nullable = false)
    private UUID etablissementId;

    @Column(name = "libelle", nullable = false, length = 20)
    private String libelle;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false, columnDefinition = "structure_pedagogique.etat_annee")
    private EtatAnnee etat;

    @Column(name = "date_demarrage")
    private Instant dateDemarrage;

    @Column(name = "date_cloture")
    private Instant dateCloture;
}
