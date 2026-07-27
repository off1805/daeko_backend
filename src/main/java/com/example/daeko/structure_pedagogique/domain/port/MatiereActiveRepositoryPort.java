package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.MatiereActive;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatiereActiveRepositoryPort {

    MatiereActive save(MatiereActive matiereActive);

    Optional<MatiereActive> findById(UUID id);

    List<MatiereActive> findByNiveauActive(UUID niveauActiveId);

    /**
     * SP-011 : verifie les 4 combinaisons d'unicite de la section 3.7
     * (avec/sans serie x referentiel/locale).
     */
    boolean existeDoublon(UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId, UUID matiereLocaleId);

    void supprimer(UUID id); // retrait autorise uniquement en EN_PREPARATION (matrice 4.6)
}
