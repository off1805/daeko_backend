package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerCycleRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierCycleRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.CycleResponse;
import com.example.daeko.referentiel.infrastructure.entity.CycleJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.SousSystemeJpaEntity;
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

    public ModifierCycleCommand toCommand(UUID id, ModifierCycleRequest request, UUID utilisateurId) {
        return new ModifierCycleCommand(
                id, request.getLibelle(), request.getLibelleEn(), request.getDescription(), utilisateurId);
    }

    public CycleResponse toResponse(Cycle domaine) {
        if (domaine == null) {
            return null;
        }
        CycleResponse response = new CycleResponse();
        response.setId(domaine.getId());
        response.setSousSystemeId(domaine.getSousSystemeId());
        response.setOrdreEnseignementId(domaine.getOrdreEnseignementId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setLibelleEn(domaine.getLibelleEn());
        response.setRang(domaine.getRang());
        response.setDureeTheoriqueAnnees(domaine.getDureeTheoriqueAnnees());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }

    public Cycle toDomain(CycleJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID sousSystemeId = entity.getSousSysteme() != null ? entity.getSousSysteme().getId() : null;
        UUID ordreId = entity.getOrdreEnseignement() != null ? entity.getOrdreEnseignement().getId() : null;

        Cycle domaine = new Cycle(
                sousSystemeId,
                ordreId,
                entity.getCode(),
                entity.getLibelle(),
                entity.getLibelleEn(),
                entity.getRang(),
                entity.getDureeTheoriqueAnnees(),
                entity.getDescription(),
                entity.getDateEntreeVigueur(),
                entity.getCreePar()
        );
        domaine.setId(entity.getId());
        domaine.setModifiePar(entity.getModifiePar());

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

        SousSystemeJpaEntity ss = new SousSystemeJpaEntity();
        ss.setId(domaine.getSousSystemeId());
        entity.setSousSysteme(ss);

        OrdreEnseignementJpaEntity oe = new OrdreEnseignementJpaEntity();
        oe.setId(domaine.getOrdreEnseignementId());
        entity.setOrdreEnseignement(oe);

        entity.setCode(domaine.getCode());
        entity.setLibelle(domaine.getLibelle());
        entity.setLibelleEn(domaine.getLibelleEn());
        entity.setRang(domaine.getRang());
        entity.setDureeTheoriqueAnnees(domaine.getDureeTheoriqueAnnees());
        entity.setDescription(domaine.getDescription());
        entity.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        entity.setDateDepreciation(domaine.getDateDepreciation());
        entity.setMotifDepreciation(domaine.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domaine.getEtat().name()));
        entity.setCreePar(domaine.getCreePar());
        entity.setModifiePar(domaine.getModifiePar());
        return entity;
    }
}
