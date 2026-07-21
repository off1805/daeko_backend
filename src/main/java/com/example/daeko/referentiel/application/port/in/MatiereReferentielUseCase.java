package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerMatiereCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierMatiereCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;

import java.util.List;
import java.util.UUID;

public interface MatiereReferentielUseCase {
    MatiereReferentiel creer(CreerMatiereCommand command);
    MatiereReferentiel modifier(ModifierMatiereCommand command);
    MatiereReferentiel deprecier(DeprecierCommand command);
    MatiereReferentiel consulterParId(UUID id);
    List<MatiereReferentiel> rechercher(EtatReferentiel etat);
}