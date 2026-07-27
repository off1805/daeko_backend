package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.CreerBrancheCommand;
import com.example.daeko.structure_pedagogique.application.dto.ModifierLibelleBrancheCommand;
import com.example.daeko.structure_pedagogique.domain.model.Branche;

import java.util.List;
import java.util.UUID;

public interface BrancheUseCase {

    Branche creer(CreerBrancheCommand commande);

    Branche modifierLibelle(ModifierLibelleBrancheCommand commande);

    /** EN_CONFIGURATION -> ACTIVE. */
    Branche activer(UUID brancheId, UUID etablissementId, UUID acteur);

    Branche suspendre(UUID brancheId, UUID etablissementId, UUID acteur);

    Branche reactiver(UUID brancheId, UUID etablissementId, UUID acteur);

    /** Motif obligatoire. */
    Branche archiver(UUID brancheId, UUID etablissementId, String motif, UUID acteur);

    Branche consulterParId(UUID brancheId, UUID etablissementId);

    List<Branche> rechercher(UUID etablissementId);
}
