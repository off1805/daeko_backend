package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.port.in.OrdreEnseignementUseCase;
import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierOrdreEnseignementCommand;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrdreEnseignementApplicationService implements OrdreEnseignementUseCase {


    @Override
    public OrdreEnseignement creer(CreerOrdreEnseignementCommand command) {

        return null;
    }

    @Override
    public OrdreEnseignement modifier(ModifierOrdreEnseignementCommand command) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public OrdreEnseignement consulterParId(UUID id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdreEnseignement> rechercher(EtatReferentiel etat) {
        return List.of();
    }
}