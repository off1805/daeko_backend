package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FiliereActiveUseCase {

    List<FiliereActive> lister(UUID configurationId, UUID etablissementId);

    ResultatActivation<FiliereActive> definirEtatCible(UUID configurationId, UUID etablissementId,
                                                        Set<UUID> filiereIds, UUID acteur);
}
