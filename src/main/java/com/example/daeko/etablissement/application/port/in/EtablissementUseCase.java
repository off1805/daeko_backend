package com.example.daeko.etablissement.application.port.in;

import com.example.daeko.etablissement.application.dto.ChangerStatutCommand;
import com.example.daeko.etablissement.application.dto.CreerEtablissementCommand;
import com.example.daeko.etablissement.application.dto.ModifierEtablissementCommand;
import com.example.daeko.etablissement.domain.model.Etablissement;
import com.example.daeko.etablissement.domain.model.StatutEtablissement;

import java.util.List;
import java.util.UUID;

public interface EtablissementUseCase {

    Etablissement creer(CreerEtablissementCommand command);

    Etablissement modifier(ModifierEtablissementCommand command);

    Etablissement changerStatut(ChangerStatutCommand command);

    Etablissement consulterParId(UUID id);

    Etablissement consulterParCode(String codeEtablissement);

    List<Etablissement> rechercher(StatutEtablissement statut);

    List<Etablissement> listerTous();
}