package com.example.daeko.structure_pedagogique.infrastructure.entity;

import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "audit_structure", schema = "structure_pedagogique")
public class AuditStructureJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "etablissement_id", nullable = false)
    private UUID etablissementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, columnDefinition = "structure_pedagogique.operation_audit_sp")
    private OperationAuditSp operation;

    @Column(name = "cible_type", nullable = false, length = 50)
    private String cibleType;

    @Column(name = "cible_id", nullable = false)
    private UUID cibleId;

    @Column(name = "utilisateur_id", nullable = false)
    private UUID utilisateurId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "valeurs_avant")
    private Map<String, Object> valeursAvant;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "valeurs_apres")
    private Map<String, Object> valeursApres;

    @Column(name = "motif")
    private String motif;

    @Column(name = "horodatage", nullable = false)
    private Instant horodatage;
}
