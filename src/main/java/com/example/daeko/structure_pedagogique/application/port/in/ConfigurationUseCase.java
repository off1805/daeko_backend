package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.CreerConfigurationCommand;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;

import java.util.UUID;

public interface ConfigurationUseCase {

    ConfigurationBrancheAnnee creer(CreerConfigurationCommand commande);

    ConfigurationBrancheAnnee consulterParId(UUID configurationId, UUID etablissementId);
}
