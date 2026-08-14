package com.example.daeko.structure_pedagogique.application.port.in;

import com.example.daeko.structure_pedagogique.application.dto.CreerMatiereLocaleCommand;
import com.example.daeko.structure_pedagogique.application.dto.ModifierMatiereLocaleCommand;
import com.example.daeko.structure_pedagogique.domain.model.MatiereLocale;

import java.util.List;
import java.util.UUID;

public interface MatiereLocaleUseCase {

    MatiereLocale creer(CreerMatiereLocaleCommand commande);

    MatiereLocale modifier(ModifierMatiereLocaleCommand commande);

    MatiereLocale deprecier(UUID matiereLocaleId, UUID etablissementId, String motif, UUID acteur);

    MatiereLocale consulterParId(UUID matiereLocaleId);

    List<MatiereLocale> rechercher(UUID brancheId);
}
