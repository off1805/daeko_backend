package com.example.daeko.structure_pedagogique.infrastructure.entity;

import com.example.daeko.structure_pedagogique.domain.model.EtatConfiguration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "configuration_branche_annee", schema = "structure_pedagogique",
    uniqueConstraints = @UniqueConstraint(name = "uq_config_branche_annee", columnNames = {"branche_id", "annee_academique_id"}))
public class ConfigurationBrancheAnneeJpaEntity extends AuditableJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "branche_id", nullable = false)
    private UUID brancheId;

    @Column(name = "annee_academique_id", nullable = false)
    private UUID anneeAcademiqueId;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false, columnDefinition = "structure_pedagogique.etat_configuration")
    private EtatConfiguration etat;

    /** Auto-reference vers la configuration source -- pas de @ManyToOne pour rester coherent avec
     *  "references en UUID, jamais d'objets embarques" applique aussi cote infra ici. */
    @Column(name = "dupliquee_depuis_id")
    private UUID dupliqueeDepuisId;

    @Column(name = "date_scellement")
    private Instant dateScellement;
}
