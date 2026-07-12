package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Superclasse JPA portant les colonnes d'audit et de cycle de vie
 * communes à toutes les entités du référentiel.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class EntiteReferentielJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EtatReferentielJpa etat = EtatReferentielJpa.ACTIVE;

    @Column(nullable = false)
    private LocalDate dateEntreeVigueur;

    @Column
    private LocalDate dateDepreciation;

    @Column(columnDefinition = "TEXT")
    private String motifDepreciation;

    @Column(nullable = false, updatable = false)
    private Instant dateCreation;

    @Column(nullable = false)
    private Instant dateModification;

    @Column(nullable = false, updatable = false)
    private UUID creePar;

    @Column(nullable = false)
    private UUID modifiePar;

    @PrePersist
    protected void onPrePersist() {
        Instant now = Instant.now();
        this.dateCreation = now;
        this.dateModification = now;
        if (this.dateEntreeVigueur == null) {
            this.dateEntreeVigueur = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.dateModification = Instant.now();
    }
}
