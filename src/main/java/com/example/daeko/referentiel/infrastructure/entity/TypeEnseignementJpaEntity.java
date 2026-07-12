package com.example.daeko.referentiel.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "type_enseignement",
    uniqueConstraints = @UniqueConstraint(name = "uq_type_enseignement_code", columnNames = "code")
)
public class TypeEnseignementJpaEntity extends EntiteReferentielJpaEntity {

    @Column(nullable = false, length = 30, unique = true)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

}
