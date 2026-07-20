package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierOrdreEnseignementCommand;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.application.dto.DeprecierOrdreEnseignementCommand;
import java.util.List;
import java.util.UUID;

public interface OrdreEnseignementUseCase {

    OrdreEnseignement creer(CreerOrdreEnseignementCommand command);

    OrdreEnseignement modifier(ModifierOrdreEnseignementCommand command);

    OrdreEnseignement consulterParId(UUID id);
    OrdreEnseignement deprecier(DeprecierOrdreEnseignementCommand command);

    List<OrdreEnseignement> rechercher(EtatReferentiel etat);
}