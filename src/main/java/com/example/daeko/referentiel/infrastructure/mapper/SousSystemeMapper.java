package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerSousSystemeCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.SousSysteme;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerSousSystemeRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.SousSystemeResponse;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.SousSystemeJpaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SousSystemeMapper {

    public SousSysteme toDomain(SousSystemeJpaEntity entity) {
        SousSysteme domaine = new SousSysteme(
                entity.getCode(), entity.getLibelle(), entity.getLibelleCourt(),
                entity.getDescription(), entity.getLanguePrincipale(),
                entity.getDateEntreeVigueur(), null);
        domaine.setId(entity.getId());
        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domaine.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }
        return domaine;
    }

    public SousSystemeJpaEntity toEntity(SousSysteme domaine) {
        SousSystemeJpaEntity entity = new SousSystemeJpaEntity();
        entity.setId(domaine.getId());
        entity.setCode(domaine.getCode());
        entity.setLibelle(domaine.getLibelle());
        entity.setLibelleCourt(domaine.getLibelleCourt());
        entity.setDescription(domaine.getDescription());
        entity.setLanguePrincipale(domaine.getLanguePrincipale());
        entity.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        entity.setDateDepreciation(domaine.getDateDepreciation());
        entity.setMotifDepreciation(domaine.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domaine.getEtat().name()));
        return entity;
    }

    public CreerSousSystemeCommand toCommand(CreerSousSystemeRequest request, UUID utilisateurId) {
        return new CreerSousSystemeCommand(
                request.getCode(), request.getLibelle(), request.getLibelleCourt(),
                request.getDescription(), request.getLanguePrincipale(), utilisateurId);
    }

    public SousSystemeResponse toResponse(SousSysteme domaine) {
        SousSystemeResponse response = new SousSystemeResponse();
        response.setId(domaine.getId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setLibelleCourt(domaine.getLibelleCourt());
        response.setDescription(domaine.getDescription());
        response.setLanguePrincipale(domaine.getLanguePrincipale());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }
}
