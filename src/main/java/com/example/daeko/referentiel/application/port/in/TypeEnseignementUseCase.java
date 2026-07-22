package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.application.dto.CreerTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;

import java.util.List;
import java.util.UUID;

public interface TypeEnseignementUseCase {
    TypeEnseignement creer(CreerTypeEnseignementCommand command);
    TypeEnseignement modifier(ModifierTypeEnseignementCommand command);
    TypeEnseignement deprecier(DeprecierCommand command);
    TypeEnseignement reactiver(ReactiverCommand command);
    TypeEnseignement consulterParId(UUID id);
    List<TypeEnseignement> rechercher(EtatReferentiel etat);
}
