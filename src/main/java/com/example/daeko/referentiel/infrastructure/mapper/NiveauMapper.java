package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.infrastructure.entity.CycleJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.NiveauJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class NiveauMapper {

    public Niveau toDomain(NiveauJpaEntity entity) {
        Niveau domain = new Niveau(
                entity.getCycle().getId(),
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleCourt(),
                entity.getLibelleEn(),
                entity.getRangDansCycle(),
                entity.getAgeTheoriqueDebut(),
                entity.getDescription(),
                entity.getDateEntreeVigueur(),
                entity.getCreePar()
        );
        domain.setId(entity.getId());
        domain.setModifiePar(entity.getModifiePar());
        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domain.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }
        return domain;
    }

    public NiveauJpaEntity toEntity(Niveau domain) {
        NiveauJpaEntity entity = new NiveauJpaEntity();
        entity.setId(domain.getId());

        CycleJpaEntity cycle = new CycleJpaEntity();
        cycle.setId(domain.getCycleId());
        entity.setCycle(cycle);

        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setLibelleCourt(domain.getLibelleCourt());
        entity.setLibelleEn(domain.getLibelleEn());
        entity.setRangDansCycle(domain.getRangDansCycle());
        entity.setAgeTheoriqueDebut(domain.getAgeTheoriqueDebut());
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