package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NiveauActiveUseCase {

    List<NiveauActive> lister(UUID configurationId, UUID etablissementId);

    ResultatActivation<NiveauActive> definirEtatCible(UUID configurationId, UUID etablissementId,
                                                       Set<UUID> niveauIds, UUID acteur);
}
