package com.example.daeko.etablissement.application.port.in;

import com.example.daeko.etablissement.application.dto.ConfigurerEnteteCommand;
import com.example.daeko.etablissement.domain.model.EnteteLigne;

import java.util.List;
import java.util.UUID;

public interface EnteteLigneUseCase {

    List<EnteteLigne> configurer(ConfigurerEnteteCommand command);

    List<EnteteLigne> listerParEtablissement(UUID etablissementId);
}