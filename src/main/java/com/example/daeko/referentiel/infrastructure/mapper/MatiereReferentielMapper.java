package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.DomaineMatiere;
import com.example.daeko.referentiel.application.dto.CreerMatiereCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.domain.model.TypeMatiereReferentiel;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerMatiereReferentielRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.MatiereReferentielResponse;
import com.example.daeko.referentiel.infrastructure.entity.DomaineMatiereJpa;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.MatiereReferentielJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.SousSystemeJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.TypeMatiereReferentielJpa;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MatiereReferentielMapper {

    public MatiereReferentiel toDomain(MatiereReferentielJpaEntity entity) {
        MatiereReferentiel domain = new MatiereReferentiel(
                entity.getSousSysteme().getId(),
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleCourt(),
                entity.getLibelleEn(),
                DomaineMatiere.valueOf(entity.getDomaine().name()),
                TypeMatiereReferentiel.valueOf(entity.getTypeMatiere().name()),
                entity.getBaremeParDefaut(),
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

    public MatiereReferentielJpaEntity toEntity(MatiereReferentiel domain) {
        MatiereReferentielJpaEntity entity = new MatiereReferentielJpaEntity();
        entity.setId(domain.getId());

        SousSystemeJpaEntity ss = new SousSystemeJpaEntity();
        ss.setId(domain.getSousSystemeId());
        entity.setSousSysteme(ss);

        entity.setCode(domain.getCode());
        entity.setLibelle(domain.getLibelle());
        entity.setLibelleCourt(domain.getLibelleCourt());
        entity.setLibelleEn(domain.getLibelleEn());
        entity.setDomaine(DomaineMatiereJpa.valueOf(domain.getDomaine().name()));
        entity.setTypeMatiere(TypeMatiereReferentielJpa.valueOf(domain.getTypeMatiere().name()));
        entity.setBaremeParDefaut(domain.getBaremeParDefaut());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());
        entity.setDateDepreciation(domain.getDateDepreciation());
        entity.setMotifDepreciation(domain.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domain.getEtat().name()));
        entity.setCreePar(domain.getCreePar());
        entity.setModifiePar(domain.getModifiePar());
        return entity;
    }

    public CreerMatiereCommand toCommand(CreerMatiereReferentielRequest request, UUID utilisateurId) {
        return new CreerMatiereCommand(
                request.getSousSystemeId(),
                request.getCode(),
                request.getLibelle(),
                request.getLibelleCourt(),
                request.getLibelleEn(),
                request.getDomaine(),
                request.getTypeMatiere(),
                request.getBaremeParDefaut(),
                request.getDescription(),
                request.getDateEntreeVigueur(),
                utilisateurId
        );
    }

    public MatiereReferentielResponse toResponse(MatiereReferentiel entity) {
        MatiereReferentielResponse response = new MatiereReferentielResponse();
        response.setId(entity.getId());
        response.setEtat(entity.getEtat() != null ? entity.getEtat().name() : null);
        response.setSousSystemeId(entity.getSousSystemeId());
        response.setCode(entity.getCode());
        response.setLibelle(entity.getLibelle());
        response.setLibelleCourt(entity.getLibelleCourt());
        response.setLibelleEn(entity.getLibelleEn());
        response.setDomaine(entity.getDomaine() != null ? entity.getDomaine().name() : null);
        response.setTypeMatiere(entity.getTypeMatiere() != null ? entity.getTypeMatiere().name() : null);
        response.setBaremeParDefaut(entity.getBaremeParDefaut());
        response.setDescription(entity.getDescription());
        response.setDateEntreeVigueur(entity.getDateEntreeVigueur());
        response.setDateDepreciation(entity.getDateDepreciation());
        return response;
    }
}
