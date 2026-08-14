package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.Classe;
import com.example.daeko.structure_pedagogique.infrastructure.entity.ClasseJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ClasseMapper {

    public ClasseJpaEntity toJpa(Classe domaine) {
        ClasseJpaEntity jpa = new ClasseJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setConfigurationId(domaine.getConfigurationId());
        jpa.setNiveauActiveId(domaine.getNiveauActiveId());
        jpa.setSerieActiveId(domaine.getSerieActiveId().orElse(null));
        jpa.setSuffixe(domaine.getSuffixe());
        jpa.setLibelleComplet(domaine.getLibelleComplet());
        jpa.setEffectifPrevu(domaine.getEffectifPrevu() == null ? null : domaine.getEffectifPrevu().shortValue());
        jpa.setSalle(domaine.getSalle());
        jpa.setEnseignantPrincipalId(domaine.getEnseignantPrincipalId().orElse(null));
        jpa.setActif(domaine.isActif());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setDateModification(domaine.getDateModification());
        jpa.setCreePar(domaine.getCreePar());
        jpa.setModifiePar(domaine.getModifiePar());
        return jpa;
    }

    public Classe toDomain(ClasseJpaEntity jpa) {
        Integer effectif = jpa.getEffectifPrevu() == null ? null : jpa.getEffectifPrevu().intValue();
        return Classe.reconstituer(
            jpa.getId(), jpa.getConfigurationId(), jpa.getNiveauActiveId(), jpa.getSerieActiveId(),
            jpa.getSuffixe(), jpa.getLibelleComplet(), effectif, jpa.getSalle(), jpa.getEnseignantPrincipalId(),
            jpa.isActif(), jpa.getDateCreation(), jpa.getDateModification(), jpa.getCreePar(), jpa.getModifiePar()
        );
    }
}
