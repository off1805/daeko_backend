package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerFiliereCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierFiliereCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;

import java.util.List;
import java.util.UUID;

public interface FiliereUseCase {
    Filiere creer(CreerFiliereCommand command);
    Filiere modifier(ModifierFiliereCommand command);
    Filiere deprecier(DeprecierCommand command);
    Filiere reactiver(ReactiverCommand command);
    Filiere consulterParId(UUID id);
    List<Filiere> rechercher(EtatReferentiel etat);
}
