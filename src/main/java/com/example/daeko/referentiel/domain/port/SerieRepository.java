package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Serie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SerieRepository {
    Serie sauvegarder(Serie serie);
    Optional<Serie> trouverParId(UUID id);
    List<Serie> rechercher(EtatReferentiel etat);
    List<Serie> rechercher(UUID filiereId, UUID niveauId, EtatReferentiel etat);
    boolean existeParCode(UUID filiereId, String code);
}
