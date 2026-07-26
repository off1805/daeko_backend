package com.example.daeko.etablissement.application.port.out;

import com.example.daeko.etablissement.domain.model.Etablissement;
import com.example.daeko.etablissement.domain.model.StatutEtablissement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EtablissementRepositoryPort {

    Etablissement save(Etablissement etablissement);

    Optional<Etablissement> findById(UUID id);

    Optional<Etablissement> findByCodeEtablissement(String codeEtablissement);

    boolean existsByNomOfficielAndArrondissementIdAndVille(String nomOfficiel, UUID arrondissementId, String ville);

    boolean existsByCodeEtablissement(String codeEtablissement);

    List<Etablissement> findByStatut(StatutEtablissement statut);

    void deleteById(UUID id);
}