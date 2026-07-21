package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerCycleRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.CycleResponse;
import com.example.daeko.referentiel.infrastructure.entity.CycleJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CycleMapper {

    public CreerCycleCommand toCommand(CreerCycleRequest request, UUID utilisateurId) {
        return new CreerCycleCommand(
                request.getSousSystemeId(),
                request.getOrdreEnseignementId(),
                request.getCode(),
                request.getLibelle(),
                request.getLibelleEn(),
                request.getRang(),
                request.getDureeTheoriqueAnnees(),
                request.getDescription(),
                request.getDateEntreeVigueur(),
                utilisateurId != null ? utilisateurId : request.getUtilisateurId()
        );
    }

    public CycleResponse toResponse(Cycle domaine) {
        if (domaine == null) {
            return null;
        }
        CycleResponse response = new CycleResponse();
        response.setId(domaine.getId());
        response.setOrdreEnseignementId(domaine.getOrdreEnseignementId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setLibelleEn(domaine.getLibelleEn());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }

    public Cycle toDomain(CycleJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID ordreId = entity.getOrdreEnseignement() != null ? entity.getOrdreEnseignement().getId() : null;

        Cycle domaine = new Cycle(
                ordreId,
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleEn(),
                entity.getDescription(),
                entity.getDateEntreeVigueur(),
                null
        );

        domaine.setId(entity.getId());

        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domaine.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }

        return domaine;
    }

    public CycleJpaEntity toEntity(Cycle domaine) {
        if (domaine == null) {
            return null;
        }
        CycleJpaEntity entity = new CycleJpaEntity();
        entity.setId(domaine.getId());

        if (domaine.getOrdreEnseignementId() != null) {
            OrdreEnseignementJpaEntity ordre = new OrdreEnseignementJpaEntity();
            ordre.setId(domaine.getOrdreEnseignementId());
            entity.setOrdreEnseignement(ordre);
        }

        entity.setCode(domaine.getCode());
        entity.setLibelle(domaine.getLibelle());
        entity.setLibelleEn(domaine.getLibelleEn());
        entity.setDescription(domaine.getDescription());
        entity.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        entity.setDateDepreciation(domaine.getDateDepreciation());
        entity.setMotifDepreciation(domaine.getMotifDepreciation());

        if (domaine.getEtat() != null) {
            entity.setEtat(EtatReferentielJpa.valueOf(domaine.getEtat().name()));
        }

        return entity;
    }
}