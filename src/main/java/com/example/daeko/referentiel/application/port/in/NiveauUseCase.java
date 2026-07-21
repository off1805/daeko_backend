package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerNiveauCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierNiveauCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;

import java.util.List;
import java.util.UUID;

public interface NiveauUseCase {
    Niveau creer(CreerNiveauCommand command);
    Niveau modifier(ModifierNiveauCommand command);
    Niveau deprecier(DeprecierCommand command);
    Niveau reactiver(ReactiverCommand command);
    Niveau consulterParId(UUID id);
    List<Niveau> rechercher(UUID cycleId, EtatReferentiel etat);
}