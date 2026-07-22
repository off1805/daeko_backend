package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierTypeEnseignementCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerTypeEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierTypeEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.TypeEnseignementResponse;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.TypeEnseignementJpaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

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

    public CreerTypeEnseignementCommand toCommand(CreerTypeEnseignementRequest request, UUID utilisateurId) {
        return new CreerTypeEnseignementCommand(
                request.getCode(), request.getLibelle(), request.getDescription(),
                LocalDate.now(), utilisateurId);
    }

    public ModifierTypeEnseignementCommand toCommand(UUID id, ModifierTypeEnseignementRequest request,
                                                      UUID utilisateurId) {
        return new ModifierTypeEnseignementCommand(
                id, request.getLibelle(), request.getDescription(), utilisateurId);
    }

    public TypeEnseignementResponse toResponse(TypeEnseignement domaine) {
        var response = new TypeEnseignementResponse();
        response.setId(domaine.getId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }
}
