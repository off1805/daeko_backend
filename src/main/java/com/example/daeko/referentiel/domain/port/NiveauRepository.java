package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NiveauRepository {
    Niveau sauvegarder(Niveau niveau);
    Optional<Niveau> trouverParId(UUID id);
    List<Niveau> rechercher(EtatReferentiel etat);
    List<Niveau> rechercher(UUID cycleId, EtatReferentiel etat);
    boolean existeParCode(UUID cycleId, String code);
}
