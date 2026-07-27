package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierOrdreEnseignementCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerOrdreEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierOrdreEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.OrdreEnseignementResponse;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class OrdreEnseignementMapper {

    public OrdreEnseignement toDomain(OrdreEnseignementJpaEntity entity) {
        if (entity == null) {
            return null;
        }
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
        if (domain == null) {
            return null;
        }
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

    public CreerOrdreEnseignementCommand toCommand(CreerOrdreEnseignementRequest request, UUID utilisateurId) {
        return new CreerOrdreEnseignementCommand(
                request.getCode(), request.getLibelle(), request.getTutelleMinisterielle(),
                request.getTutelleMinisterielleEn(), request.getRang(), request.getDescription(),
                LocalDate.now(), utilisateurId);
    }

    public ModifierOrdreEnseignementCommand toCommand(UUID id, ModifierOrdreEnseignementRequest request,
                                                      UUID utilisateurId) {
        return new ModifierOrdreEnseignementCommand(
                id, request.getLibelle(), request.getTutelleMinisterielle(),
                request.getTutelleMinisterielleEn(), request.getDescription(), utilisateurId);
    }

    public OrdreEnseignementResponse toResponse(OrdreEnseignement domaine) {
        OrdreEnseignementResponse response = new OrdreEnseignementResponse();
        response.setId(domaine.getId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setTutelleMinisterielle(domaine.getTutelleMinisterielle());
        response.setTutelleMinisterielleEn(domaine.getTutelleMinisterielleEn());
        response.setRang(domaine.getRang());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        return response;
    }
}
