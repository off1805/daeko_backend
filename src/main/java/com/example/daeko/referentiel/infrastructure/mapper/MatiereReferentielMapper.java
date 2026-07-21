package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerMatiereCommand;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerMatiereReferentielRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.MatiereReferentielResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MatiereReferentielMapper {

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