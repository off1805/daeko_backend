package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CycleRepository {
    Cycle sauvegarder(Cycle cycle);
    Optional<Cycle> trouverParId(UUID id);
    List<Cycle> rechercher(EtatReferentiel etat);
    List<Cycle> rechercher(UUID sousSystemeId, UUID ordreEnseignementId, EtatReferentiel etat);
    boolean existeParCode(String code);
}
