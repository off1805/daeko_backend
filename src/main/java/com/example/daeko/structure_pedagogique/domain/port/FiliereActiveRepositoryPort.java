package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;

import java.util.List;
import java.util.UUID;

public interface FiliereActiveRepositoryPort {

    FiliereActive save(FiliereActive filiereActive);

    List<FiliereActive> findByConfiguration(UUID configurationId);

    void supprimer(UUID id); // retrait autorise uniquement en EN_PREPARATION (matrice 4.6)
}
