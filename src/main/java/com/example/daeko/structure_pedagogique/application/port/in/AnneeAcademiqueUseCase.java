package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.CreerAnneeAcademiqueCommand;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;

import java.util.List;
import java.util.UUID;

public interface AnneeAcademiqueUseCase {

    AnneeAcademique creer(CreerAnneeAcademiqueCommand commande);

    /** EN_PREPARATION -> EN_COURS. */
    AnneeAcademique demarrer(UUID anneeId, UUID etablissementId, UUID acteur);

    /**
     * Irreversible, exige une confirmation egale au libelle exact de l'annee. Scelle toutes les
     * configurations de l'annee dans la meme transaction (TC-21).
     */
    AnneeAcademique cloturer(UUID anneeId, UUID etablissementId, String confirmation, UUID acteur);

    AnneeAcademique consulterParId(UUID anneeId, UUID etablissementId);

    List<AnneeAcademique> rechercher(UUID etablissementId);
}
