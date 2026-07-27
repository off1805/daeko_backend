package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NiveauActiveRepositoryPort {

    NiveauActive save(NiveauActive niveauActive);

    Optional<NiveauActive> findById(UUID id);

    List<NiveauActive> findByConfiguration(UUID configurationId);

    void supprimer(UUID id);
}
