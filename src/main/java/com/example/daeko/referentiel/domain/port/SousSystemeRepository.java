package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.SousSysteme;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SousSystemeRepository {
    SousSysteme sauvegarder(SousSysteme sousSysteme);
    Optional<SousSysteme> trouverParId(UUID id);
    List<SousSysteme> rechercher(EtatReferentiel etat);
    boolean existeParCode(String code);
}
