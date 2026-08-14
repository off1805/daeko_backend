package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.event;

import com.example.daeko.structure_pedagogique.infrastructure.entity.OutboxStructureJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringOutboxStructureJpaRepository extends JpaRepository<OutboxStructureJpaEntity, UUID> {

    List<OutboxStructureJpaEntity> findByPublieLeIsNull();
}
