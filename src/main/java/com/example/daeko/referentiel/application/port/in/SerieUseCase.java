package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerSerieCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierSerieCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Serie;

import java.util.List;
import java.util.UUID;

public interface SerieUseCase {
    Serie creer(CreerSerieCommand command);
    Serie modifier(ModifierSerieCommand command);
    Serie deprecier(DeprecierCommand command);
    Serie consulterParId(UUID id);
    List<Serie> rechercher(EtatReferentiel etat);
}