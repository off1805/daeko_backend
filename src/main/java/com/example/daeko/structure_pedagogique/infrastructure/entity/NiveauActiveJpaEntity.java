package com.example.daeko.structure_pedagogique.infrastructure.entity;

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
@Table(name = "niveau_active", schema = "structure_pedagogique",
    uniqueConstraints = @UniqueConstraint(name = "uq_niveau_active", columnNames = {"configuration_id", "niveau_id"}))
public class NiveauActiveJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "configuration_id", nullable = false)
    private UUID configurationId;

    @Column(name = "niveau_id", nullable = false)
    private UUID niveauId;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private Instant dateCreation;

    @Column(name = "cree_par", nullable = false, updatable = false)
    private UUID creePar;
}
