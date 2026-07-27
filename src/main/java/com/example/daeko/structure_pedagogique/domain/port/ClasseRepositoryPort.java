package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.Classe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClasseRepositoryPort {

    Classe save(Classe classe);

    Optional<Classe> findById(UUID id);

    List<Classe> findByConfiguration(UUID configurationId, boolean actifSeulement);

    boolean existeSuffixe(UUID configurationId, UUID niveauActiveId, UUID serieActiveId, String suffixe);
}
