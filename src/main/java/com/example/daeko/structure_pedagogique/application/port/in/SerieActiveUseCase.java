package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.domain.model.SerieActive;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SerieActiveUseCase {

    List<SerieActive> lister(UUID niveauActiveId, UUID etablissementId);

    ResultatActivation<SerieActive> definirEtatCible(UUID niveauActiveId, UUID etablissementId,
                                                      Set<UUID> serieIds, UUID acteur);
}
