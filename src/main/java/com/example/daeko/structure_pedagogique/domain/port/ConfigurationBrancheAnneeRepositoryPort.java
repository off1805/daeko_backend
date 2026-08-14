package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfigurationBrancheAnneeRepositoryPort {

    ConfigurationBrancheAnnee save(ConfigurationBrancheAnnee configuration);

    Optional<ConfigurationBrancheAnnee> findById(UUID id);

    Optional<ConfigurationBrancheAnnee> findByBrancheEtAnnee(UUID brancheId, UUID anneeAcademiqueId);

    List<ConfigurationBrancheAnnee> findByAnneeAcademique(UUID anneeAcademiqueId);

    Optional<ConfigurationBrancheAnnee> findDerniereConfigurationAvant(UUID brancheId, UUID anneeAcademiqueCibleId);
}
