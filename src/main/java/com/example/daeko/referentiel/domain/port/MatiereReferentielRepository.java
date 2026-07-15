package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatiereReferentielRepository {
    MatiereReferentiel sauvegarder(MatiereReferentiel matiere);
    Optional<MatiereReferentiel> trouverParId(UUID id);
    List<MatiereReferentiel> rechercher(EtatReferentiel etat);
    List<MatiereReferentiel> rechercher(UUID sousSystemeId, EtatReferentiel etat);
    boolean existeParCode(UUID sousSystemeId, String code);
}
