package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.infrastructure.entity.CycleJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.SousSystemeJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CycleMapper {

    public Cycle toDomain(CycleJpaEntity entity) {
        Cycle domain = new Cycle(
                entity.getSousSysteme().getId(),
                entity.getOrdreEnseignement().getId(),
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleEn(),
                entity.getRang(),
                entity.getDureeTheoriqueAnnees(),
                entity.getDescription(),
                entity.getDateEntreeVigueur()
        );
        domain.setId(entity.getId());
        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domain.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }
        return domain;
    }

    public CycleJpaEntity toEntity(Cycle domain) {
        CycleJpaEntity entity = new CycleJpaEntity();
        entity.setId(domain.getId());
        
        SousSystemeJpaEntity ss = new SousSystemeJpaEntity();
        ss.setId(domain.getSousSystemeId());
        entity.setSousSysteme(ss);
        
        OrdreEnseignementJpaEntity oe = new OrdreEnseignementJpaEntity();
        oe.setId(domain.getOrdreEnseignementId());
        entity.setOrdreEnseignement(oe);
        
        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setLibelleEn(domain.getLibelleEn());
        entity.setRang(domain.getRang());
        entity.setDureeTheoriqueAnnees(domain.getDureeTheoriqueAnnees());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());
        entity.setDateDepreciation(domain.getDateDepreciation());
        entity.setMotifDepreciation(domain.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domain.getEtat().name()));
        return entity;
    }
}
