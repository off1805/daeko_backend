package com.example.daeko.etablissement.application.port.out;

import com.example.daeko.etablissement.domain.model.Signataire;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SignataireRepositoryPort {

    Signataire save(Signataire signataire);

    Optional<Signataire> findById(UUID id);

    List<Signataire> findByEtablissementId(UUID etablissementId);

    Optional<Signataire> findPrincipalByEtablissementId(UUID etablissementId);

    void deleteById(UUID id);
}