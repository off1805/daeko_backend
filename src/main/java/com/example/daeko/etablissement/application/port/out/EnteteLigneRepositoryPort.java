package com.example.daeko.etablissement.application.port.out;

import com.example.daeko.etablissement.domain.model.EnteteLigne;

import java.util.List;
import java.util.UUID;

public interface EnteteLigneRepositoryPort {

    List<EnteteLigne> saveAll(List<EnteteLigne> lignes);

    List<EnteteLigne> findByEtablissementIdOrderByOrdreAsc(UUID etablissementId);

    void deleteByEtablissementId(UUID etablissementId);
}