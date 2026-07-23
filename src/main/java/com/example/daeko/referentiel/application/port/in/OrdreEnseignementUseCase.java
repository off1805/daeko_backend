package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;

import java.util.List;
import java.util.UUID;

public interface OrdreEnseignementUseCase {
    OrdreEnseignement creer(CreerOrdreEnseignementCommand command);
    OrdreEnseignement modifier(ModifierOrdreEnseignementCommand command);
    OrdreEnseignement deprecier(DeprecierCommand command);
    OrdreEnseignement reactiver(ReactiverCommand command);
    OrdreEnseignement consulterParId(UUID id);
    List<OrdreEnseignement> rechercher(EtatReferentiel etat);
}