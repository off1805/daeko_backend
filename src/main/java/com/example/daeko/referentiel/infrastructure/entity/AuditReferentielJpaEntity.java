package com.example.daeko.referentiel.infrastructure.entity;

import com.example.daeko.referentiel.domain.model.OperationAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_referentiel", schema = "referentiel")
@Getter
@Setter
public class AuditReferentielJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "type_entite", nullable = false, length = 50)
    private String typeEntite;

    @Column(name = "entite_id", nullable = false)
    private UUID entiteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationAudit operation;

    @Column(name = "utilisateur_id", nullable = false)
    private UUID utilisateurId;

    @Column(nullable = false)
    private Instant horodatage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "valeurs_avant", columnDefinition = "jsonb")
    private String valeursAvant;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "valeurs_apres", columnDefinition = "jsonb")
    private String valeursApres;

    @Column(columnDefinition = "TEXT")
    private String motif;

    @PrePersist
    protected void onPrePersist() {
        this.horodatage = Instant.now();
    }
}