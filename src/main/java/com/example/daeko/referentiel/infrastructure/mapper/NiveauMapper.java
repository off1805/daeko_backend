package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerNiveauCommand;
import com.example.daeko.referentiel.application.dto.ModifierNiveauCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerNiveauRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierNiveauRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.NiveauResponse;
import com.example.daeko.referentiel.infrastructure.entity.CycleJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.NiveauJpaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public CreerNiveauCommand toCommand(CreerNiveauRequest request, UUID utilisateurId) {
        return new com.example.daeko.referentiel.application.dto.CreerNiveauCommand(
                request.getCycleId(), request.getCode(), request.getLibelle(),
                request.getLibelleCourt(), request.getLibelleEn(), request.getRangDansCycle(),
                request.getAgeTheoriqueDebut(), request.getDescription(),
                java.time.LocalDate.now(), utilisateurId);
    }

    public ModifierNiveauCommand toCommand(UUID id, ModifierNiveauRequest request, UUID utilisateurId) {
        return new ModifierNiveauCommand(
                id, request.getLibelle(), request.getLibelleCourt(), request.getLibelleEn(),
                request.getAgeTheoriqueDebut(), request.getDescription(), utilisateurId);
    }

    public NiveauResponse toResponse(Niveau domaine) {
        var response = new NiveauResponse();
        response.setId(domaine.getId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setCycleId(domaine.getCycleId());
        response.setCode(domaine.getCode());
        response.setLibelle(domaine.getLibelle());
        response.setLibelleCourt(domaine.getLibelleCourt());
        response.setLibelleEn(domaine.getLibelleEn());
        response.setRangDansCycle(domaine.getRangDansCycle());
        response.setAgeTheoriqueDebut(domaine.getAgeTheoriqueDebut());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }
}