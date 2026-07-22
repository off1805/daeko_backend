package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;

import java.util.List;
import java.util.UUID;

public interface CycleUseCase {

    Cycle creer(CreerCycleCommand command);

    Cycle modifier(ModifierCycleCommand command);

    Cycle deprecier(DeprecierCommand command);

    Cycle reactiver(ReactiverCommand command);

    Cycle consulterParId(UUID id);

    List<Cycle> rechercher(EtatReferentiel etat);
}
