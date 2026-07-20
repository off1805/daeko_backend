package com.example.daeko.referentiel.application.port.out;

import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import java.util.Optional;
import java.util.UUID;

public interface TypeEnseignementRepository {
    TypeEnseignement save(TypeEnseignement typeEnseignement);
    Optional<TypeEnseignement> findById(UUID id);
}