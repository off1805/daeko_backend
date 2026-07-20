
package com.example.daeko.referentiel.domain.ports.in;

import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCycleCommand;
import java.util.List;
import java.util.UUID;

public interface CycleUseCase {


    Cycle creer(CreerCycleCommand command);

    Cycle modifier(ModifierCycleCommand command);

    Cycle consulterParId(UUID id);
    Cycle deprecier(DeprecierCycleCommand command);
    List<Cycle> rechercher(EtatReferentiel etat);

}