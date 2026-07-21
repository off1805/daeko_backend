package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class OrdreEnseignementMapper {

    public OrdreEnseignement toDomain(OrdreEnseignementJpaEntity entity) {
        OrdreEnseignement domain = new OrdreEnseignement(
                entity.getCode(),
                entity.getLibelle(),
                entity.getTutelleMinisterielle(),
                entity.getTutelleMinisterielleEn(),
                entity.getRang(),
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

    public OrdreEnseignementJpaEntity toEntity(OrdreEnseignement domain) {
        OrdreEnseignementJpaEntity entity = new OrdreEnseignementJpaEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setTutelleMinisterielle(domain.getTutelleMinisterielle());
        entity.setTutelleMinisterielleEn(domain.getTutelleMinisterielleEn());
        entity.setRang(domain.getRang());
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
