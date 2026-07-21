package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerSerieCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerSerieRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.SerieResponse;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.FiliereJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.SerieJpaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SerieMapper {

    public Serie toDomain(SerieJpaEntity entity) {
        UUID filiereId = entity.getFiliere() != null ? entity.getFiliere().getId() : null;

        Serie domaine = new Serie(
                filiereId,
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleEn(),
                entity.getDescription(),
                entity.getDateEntreeVigueur());

        domaine.setId(entity.getId());

        if (entity.getEtat() == EtatReferentielJpa.DEPRECATED) {
            domaine.deprecier(entity.getMotifDepreciation(), entity.getDateDepreciation());
        }

        return domaine;
    }

    public SerieJpaEntity toEntity(Serie domaine) {
        SerieJpaEntity entity = new SerieJpaEntity();
        entity.setId(domaine.getId());

        // Attribution de la Filière
        if (domaine.getTypeEnseignementId() != null) {
            FiliereJpaEntity filiere = new FiliereJpaEntity();
            filiere.setId(domaine.getTypeEnseignementId());
            entity.setFiliere(filiere);
        }

        entity.setCode(domaine.getCode());
        entity.setLibelle(domaine.getLibelle());
        entity.setLibelleCourt(domaine.getLibelleCourt());
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

    public CreerSerieCommand toCommand(CreerSerieRequest request, UUID utilisateurId) {
        return new CreerSerieCommand(
                request.getTypeEnseignementId(),
                request.getCode(),
                request.getLibelle(),
                request.getLibelleEn(),
                request.getDescription(),
                request.getDateEntreeVigueur(),
                utilisateurId != null ? utilisateurId : request.getUtilisateurId());
    }

    public SerieResponse toResponse(Serie domaine) {
        SerieResponse response = new SerieResponse();
        response.setId(domaine.getId());
        response.setTypeEnseignementId(domaine.getTypeEnseignementId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setLibelleEn(domaine.getLibelleEn());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }
}