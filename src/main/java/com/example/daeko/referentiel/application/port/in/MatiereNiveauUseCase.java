package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerMatiereNiveauCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.domain.model.MatiereReferentielNiveau;

import java.util.List;
import java.util.UUID;

public interface MatiereNiveauUseCase {
    MatiereReferentielNiveau creer(CreerMatiereNiveauCommand command);
    MatiereReferentielNiveau deprecier(DeprecierCommand command);
    MatiereReferentielNiveau consulterParId(UUID id);
    List<MatiereReferentielNiveau> rechercher(UUID niveauId, UUID serieId);
}