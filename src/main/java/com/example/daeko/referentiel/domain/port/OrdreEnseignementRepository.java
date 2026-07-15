package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdreEnseignementRepository {

    OrdreEnseignement sauvegarder(OrdreEnseignement ordreEnseignement);

    Optional<OrdreEnseignement> trouverParId(UUID id);

    List<OrdreEnseignement> rechercher(EtatReferentiel etat);

    boolean existeParCode(String code);
}