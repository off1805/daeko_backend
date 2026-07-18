package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.FiliereJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.NiveauJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.SerieJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SerieMapper {

    public Serie toDomain(SerieJpaEntity entity) {
        Serie domain = new Serie(
                entity.getFiliere().getId(),
                entity.getNiveauApparition().getId(),
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleCourt(),
                entity.getLibelleEn(),
                entity.getDescription(),
                entity.getDateEntreeVigueur()
        );
        domain.setId(entity.getId());
        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domain.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }
        return domain;
    }

    public SerieJpaEntity toEntity(Serie domain) {
        SerieJpaEntity entity = new SerieJpaEntity();
        entity.setId(domain.getId());
        
        FiliereJpaEntity filiere = new FiliereJpaEntity();
        filiere.setId(domain.getFiliereId());
        entity.setFiliere(filiere);
        
        NiveauJpaEntity niveau = new NiveauJpaEntity();
        niveau.setId(domain.getNiveauApparitionId());
        entity.setNiveauApparition(niveau);
        
        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setLibelleCourt(domain.getLibelleCourt());
        entity.setLibelleEn(domain.getLibelleEn());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());
        entity.setDateDepreciation(domain.getDateDepreciation());
        entity.setMotifDepreciation(domain.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domain.getEtat().name()));
        return entity;
    }
}
