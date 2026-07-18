package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class OrdreEnseignementMapper {

    public OrdreEnseignement toDomain(OrdreEnseignementJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new OrdreEnseignement(
                entity.getCode(),
                entity.getLibelle(),
                entity.getTutelleMinisterielle(),
                entity.getTutelleMinisterielleEn(),
                entity.getRang(),
                entity.getDescription(),
                entity.getDateEntreeVigueur()
        );
    }

    public OrdreEnseignementJpaEntity toEntity(OrdreEnseignement domain) {
        if (domain == null) {
            return null;
        }

        OrdreEnseignementJpaEntity entity = new OrdreEnseignementJpaEntity();
        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setTutelleMinisterielle(domain.getTutelleMinisterielle());
        entity.setTutelleMinisterielleEn(domain.getTutelleMinisterielleEn());
        entity.setRang(domain.getRang());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());

        return entity;
    }
}