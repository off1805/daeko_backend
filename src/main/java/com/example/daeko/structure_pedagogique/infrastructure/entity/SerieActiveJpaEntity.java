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
@Table(name = "serie_active", schema = "structure_pedagogique",
    uniqueConstraints = @UniqueConstraint(name = "uq_serie_active", columnNames = {"niveau_active_id", "serie_id"}))
public class SerieActiveJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "niveau_active_id", nullable = false)
    private UUID niveauActiveId;

    @Column(name = "serie_id", nullable = false)
    private UUID serieId;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private Instant dateCreation;

    @Column(name = "cree_par", nullable = false, updatable = false)
    private UUID creePar;
}
