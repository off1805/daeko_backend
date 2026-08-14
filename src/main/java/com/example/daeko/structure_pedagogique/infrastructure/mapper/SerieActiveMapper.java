package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.SerieActive;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.SerieActiveResponse;
import com.example.daeko.structure_pedagogique.infrastructure.entity.SerieActiveJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SerieActiveMapper {

    public SerieActiveJpaEntity toJpa(SerieActive domaine) {
        SerieActiveJpaEntity jpa = new SerieActiveJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setNiveauActiveId(domaine.getNiveauActiveId());
        jpa.setSerieId(domaine.getSerieId());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setCreePar(domaine.getCreePar());
        return jpa;
    }

    public SerieActive toDomain(SerieActiveJpaEntity jpa) {
        return SerieActive.reconstituer(jpa.getId(), jpa.getNiveauActiveId(), jpa.getSerieId(),
            jpa.getDateCreation(), jpa.getCreePar());
    }

    public SerieActiveResponse toResponse(SerieActive domaine) {
        SerieActiveResponse reponse = new SerieActiveResponse();
        reponse.setId(domaine.getId());
        reponse.setNiveauActiveId(domaine.getNiveauActiveId());
        reponse.setSerieId(domaine.getSerieId());
        reponse.setDateCreation(domaine.getDateCreation());
        return reponse;
    }
}

