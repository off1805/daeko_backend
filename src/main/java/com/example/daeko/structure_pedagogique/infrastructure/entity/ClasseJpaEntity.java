package com.example.daeko.structure_pedagogique.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "classe", schema = "structure_pedagogique")
public class ClasseJpaEntity extends AuditableJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "configuration_id", nullable = false)
    private UUID configurationId;

    @Column(name = "niveau_active_id", nullable = false)
    private UUID niveauActiveId;

    @Column(name = "serie_active_id")
    private UUID serieActiveId;

    @Column(name = "suffixe", nullable = false, length = 10)
    private String suffixe;

    @Column(name = "libelle_complet", nullable = false, length = 80)
    private String libelleComplet;

    @Column(name = "effectif_prevu")
    private Short effectifPrevu;

    @Column(name = "salle", length = 50)
    private String salle;

    /** Reference LOGIQUE au module Personnes -- volontairement pas de FK physique (section 2). */
    @Column(name = "enseignant_principal_id")
    private UUID enseignantPrincipalId;

    @Column(name = "actif", nullable = false)
    private boolean actif;
}
