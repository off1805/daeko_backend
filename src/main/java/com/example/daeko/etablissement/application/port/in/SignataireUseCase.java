package com.example.daeko.etablissement.application.port.in;

import com.example.daeko.etablissement.application.dto.CreerSignataireCommand;
import com.example.daeko.etablissement.application.dto.ModifierSignataireCommand;
import com.example.daeko.etablissement.domain.model.Signataire;

import java.util.List;
import java.util.UUID;

public interface SignataireUseCase {

    Signataire ajouter(CreerSignataireCommand command);

    Signataire modifier(ModifierSignataireCommand command);

    void definirPrincipal(UUID etablissementId, UUID signataireId, UUID utilisateurId);

    void supprimer(UUID signataireId, UUID utilisateurId);

    Signataire consulterParId(UUID id);

    List<Signataire> listerParEtablissement(UUID etablissementId);
}