package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.TypeEnseignement; // Import à ajuster selon le dossier de tes modèles
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeEnseignementRepository {

    TypeEnseignement sauvegarder(TypeEnseignement typeEnseignement);

    Optional<TypeEnseignement> trouverParId(UUID id);

    List<TypeEnseignement> rechercher(EtatReferentiel etat);

    boolean existeParCode(String code);
}