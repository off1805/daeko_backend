package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.MatiereActive;
import com.example.daeko.structure_pedagogique.infrastructure.entity.MatiereActiveJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MatiereActiveMapper {

    public MatiereActiveJpaEntity toJpa(MatiereActive domaine) {
        MatiereActiveJpaEntity jpa = new MatiereActiveJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setNiveauActiveId(domaine.getNiveauActiveId());
        jpa.setSerieActiveId(domaine.getSerieActiveId().orElse(null));
        jpa.setMatiereReferentielId(domaine.getMatiereReferentielId().orElse(null));
        jpa.setMatiereLocaleId(domaine.getMatiereLocaleId().orElse(null));
        jpa.setCoefficient(domaine.getCoefficient());
        jpa.setBareme(domaine.getBareme() == null ? null : domaine.getBareme().shortValue());
        jpa.setEstObligatoire(domaine.isEstObligatoire());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setDateModification(domaine.getDateModification());
        jpa.setCreePar(domaine.getCreePar());
        jpa.setModifiePar(domaine.getModifiePar());
        return jpa;
    }

    public MatiereActive toDomain(MatiereActiveJpaEntity jpa) {
        Integer bareme = jpa.getBareme() == null ? null : jpa.getBareme().intValue();
        return MatiereActive.reconstituer(
            jpa.getId(), jpa.getNiveauActiveId(), jpa.getSerieActiveId(), jpa.getMatiereReferentielId(),
            jpa.getMatiereLocaleId(), jpa.getCoefficient(), bareme, jpa.isEstObligatoire(),
            jpa.getDateCreation(), jpa.getDateModification(), jpa.getCreePar(), jpa.getModifiePar()
        );
    }
}
