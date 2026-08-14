package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.Branche;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrancheRepositoryPort {

    Branche save(Branche branche);

    Optional<Branche> findById(UUID id);

    Optional<Branche> findByIdEtEtablissement(UUID id, UUID etablissementId);

    List<Branche> findByEtablissement(UUID etablissementId);

    boolean existsByTriplet(UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId, UUID typeEnseignementId);
}
