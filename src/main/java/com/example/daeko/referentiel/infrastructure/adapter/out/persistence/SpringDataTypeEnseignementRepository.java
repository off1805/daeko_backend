package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import java.util.UUID;

public interface SpringDataTypeEnseignementRepository extends JpaRepository<TypeEnseignement, UUID> {
}