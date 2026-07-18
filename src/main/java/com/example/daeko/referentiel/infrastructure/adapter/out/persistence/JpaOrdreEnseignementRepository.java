package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface JpaOrdreEnseignementRepository extends JpaRepository<OrdreEnseignementJpaEntity, UUID> {

    boolean existsByCode(String code);

    Optional<OrdreEnseignementJpaEntity> findByCode(String code);
}