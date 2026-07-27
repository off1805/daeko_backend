package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnneeAcademiqueRepositoryPort {

    AnneeAcademique save(AnneeAcademique annee);

    Optional<AnneeAcademique> findById(UUID id);

    Optional<AnneeAcademique> findByIdEtEtablissement(UUID id, UUID etablissementId);

    List<AnneeAcademique> findByEtablissement(UUID etablissementId);

    Optional<AnneeAcademique> findEnCoursByEtablissement(UUID etablissementId);

    boolean existsByEtablissementAndLibelle(UUID etablissementId, String libelle);
}
