package com.example.daeko.structure_pedagogique.infrastructure.entity;

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
@Table(name = "outbox_structure", schema = "structure_pedagogique")
public class OutboxStructureJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "evenement", nullable = false, length = 60)
    private String evenement;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false)
    private Map<String, Object> payload;

    @Column(name = "cree_le", nullable = false)
    private Instant creeLe;

    @Column(name = "publie_le")
    private Instant publieLe;
}
