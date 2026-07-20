package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerSousSystemeCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierSousSystemeCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.SousSysteme;

import java.util.List;
import java.util.UUID;

public interface SousSystemeUseCase {
    SousSysteme creer(CreerSousSystemeCommand command);
    SousSysteme modifier(ModifierSousSystemeCommand command);
    SousSysteme deprecier(DeprecierCommand command);
    SousSysteme reactiver(ReactiverCommand command);
    SousSysteme consulterParId(UUID id);
    List<SousSysteme> rechercher(EtatReferentiel etat);
}