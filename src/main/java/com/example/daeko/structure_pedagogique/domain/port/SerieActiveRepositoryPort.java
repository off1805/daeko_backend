package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.SerieActive;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SerieActiveRepositoryPort {

    SerieActive save(SerieActive serieActive);

    Optional<SerieActive> findById(UUID id);

    List<SerieActive> findByNiveauActive(UUID niveauActiveId);

    void supprimer(UUID id);
}
