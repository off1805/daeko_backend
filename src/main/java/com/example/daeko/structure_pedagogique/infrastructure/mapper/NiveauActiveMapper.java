package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;
import com.example.daeko.structure_pedagogique.infrastructure.entity.NiveauActiveJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class NiveauActiveMapper {

    public NiveauActiveJpaEntity toJpa(NiveauActive domaine) {
        NiveauActiveJpaEntity jpa = new NiveauActiveJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setConfigurationId(domaine.getConfigurationId());
        jpa.setNiveauId(domaine.getNiveauId());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setCreePar(domaine.getCreePar());
        return jpa;
    }

    public NiveauActive toDomain(NiveauActiveJpaEntity jpa) {
        return NiveauActive.reconstituer(jpa.getId(), jpa.getConfigurationId(), jpa.getNiveauId(),
            jpa.getDateCreation(), jpa.getCreePar());
    }
}
