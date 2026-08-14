package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.FiliereActiveResponse;
import com.example.daeko.structure_pedagogique.infrastructure.entity.FiliereActiveJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class FiliereActiveMapper {

    public FiliereActiveJpaEntity toJpa(FiliereActive domaine) {
        FiliereActiveJpaEntity jpa = new FiliereActiveJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setConfigurationId(domaine.getConfigurationId());
        jpa.setFiliereId(domaine.getFiliereId());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setCreePar(domaine.getCreePar());
        return jpa;
    }

    public FiliereActive toDomain(FiliereActiveJpaEntity jpa) {
        return FiliereActive.reconstituer(jpa.getId(), jpa.getConfigurationId(), jpa.getFiliereId(),
            jpa.getDateCreation(), jpa.getCreePar());
    }

    public FiliereActiveResponse toResponse(FiliereActive domaine) {
        FiliereActiveResponse reponse = new FiliereActiveResponse();
        reponse.setId(domaine.getId());
        reponse.setConfigurationId(domaine.getConfigurationId());
        reponse.setFiliereId(domaine.getFiliereId());
        reponse.setDateCreation(domaine.getDateCreation());
        return reponse;
    }
}

