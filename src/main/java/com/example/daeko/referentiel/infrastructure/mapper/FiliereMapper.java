package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerFiliereCommand;
import com.example.daeko.referentiel.application.dto.ModifierFiliereCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerFiliereRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierFiliereRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.FiliereResponse;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.FiliereJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.TypeEnseignementJpaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

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
        entity.setCreePar(domain.getCreePar());
        entity.setModifiePar(domain.getModifiePar());
        return entity;
    }

    public CreerFiliereCommand toCommand(CreerFiliereRequest request, UUID utilisateurId) {
        return new CreerFiliereCommand(
                request.getOrdreEnseignementId(), request.getTypeEnseignementId(), request.getCode(),
                request.getLibelle(), request.getLibelleEn(), request.getDescription(),
                LocalDate.now(), utilisateurId);
    }

    public ModifierFiliereCommand toCommand(UUID id, ModifierFiliereRequest request, UUID utilisateurId) {
        return new ModifierFiliereCommand(
                id, request.getLibelle(), request.getLibelleEn(), request.getDescription(), utilisateurId);
    }

    public FiliereResponse toResponse(Filiere domaine) {
        var response = new FiliereResponse();
        response.setId(domaine.getId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setOrdreEnseignementId(domaine.getOrdreEnseignementId());
        response.setTypeEnseignementId(domaine.getTypeEnseignementId());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setLibelleEn(domaine.getLibelleEn());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }
}