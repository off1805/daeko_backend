package com.example.daeko.structure_pedagogique.domain.port;

import com.example.daeko.structure_pedagogique.domain.model.MatiereLocale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatiereLocaleRepositoryPort {

    MatiereLocale save(MatiereLocale matiereLocale);

    Optional<MatiereLocale> findById(UUID id);

    List<MatiereLocale> findByBranche(UUID brancheId);

    boolean existsByBrancheAndCode(UUID brancheId, String code);
}
