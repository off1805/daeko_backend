package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.Filiere; // Import à ajuster selon le dossier de tes modèles
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FiliereRepository {

    Filiere sauvegarder(Filiere filiere);

    Optional<Filiere> trouverParId(UUID id);

    List<Filiere> rechercher(EtatReferentiel etat);

    boolean existeParCode(String code);
}