package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.audit;

import com.example.daeko.structure_pedagogique.infrastructure.entity.AuditStructureJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringAuditStructureJpaRepository extends JpaRepository<AuditStructureJpaEntity, UUID> {
}
