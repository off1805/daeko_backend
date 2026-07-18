package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.FiliereJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.TypeEnseignementJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {

    public Filiere toDomain(FiliereJpaEntity entity) {
        Filiere domain = new Filiere(
                entity.getOrdreEnseignement().getId(),
                entity.getTypeEnseignement().getId(),
                entity.getCode(),
                entity.getLibelle(),
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

    public FiliereJpaEntity toEntity(Filiere domain) {
        FiliereJpaEntity entity = new FiliereJpaEntity();
        entity.setId(domain.getId());
        
        OrdreEnseignementJpaEntity oe = new OrdreEnseignementJpaEntity();
        oe.setId(domain.getOrdreEnseignementId());
        entity.setOrdreEnseignement(oe);
        
        TypeEnseignementJpaEntity te = new TypeEnseignementJpaEntity();
        te.setId(domain.getTypeEnseignementId());
        entity.setTypeEnseignement(te);
        
        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setLibelleEn(domain.getLibelleEn());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());
        entity.setDateDepreciation(domain.getDateDepreciation());
        entity.setMotifDepreciation(domain.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domain.getEtat().name()));
        return entity;
    }
}
