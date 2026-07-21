package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.TypeEnseignementJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TypeEnseignementMapper {

    public TypeEnseignement toDomain(TypeEnseignementJpaEntity entity) {
        TypeEnseignement domain = new TypeEnseignement(
                entity.getCode(),
                entity.getLibelle(),
                entity.getDescription(),
                entity.getDateEntreeVigueur()
        );
        domain.setId(entity.getId());
        domain.setCreePar(entity.getCreePar());
        domain.setModifiePar(entity.getModifiePar());
        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domain.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }
        return domain;
    }

    public TypeEnseignementJpaEntity toEntity(TypeEnseignement domain) {
        TypeEnseignementJpaEntity entity = new TypeEnseignementJpaEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());
        entity.setDateDepreciation(domain.getDateDepreciation());
        entity.setMotifDepreciation(domain.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domain.getEtat().name()));
        entity.setCreePar(domain.getCreePar());
        entity.setModifiePar(domain.getModifiePar());
        return entity;
    }
}
