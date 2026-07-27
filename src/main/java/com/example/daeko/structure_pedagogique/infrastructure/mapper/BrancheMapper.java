package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.BrancheResponse;
import com.example.daeko.structure_pedagogique.infrastructure.entity.BrancheJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class BrancheMapper {

    public BrancheJpaEntity toJpa(Branche domaine) {
        BrancheJpaEntity jpa = new BrancheJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setEtablissementId(domaine.getEtablissementId());
        jpa.setSousSystemeId(domaine.getSousSystemeId());
        jpa.setOrdreEnseignementId(domaine.getOrdreEnseignementId());
        jpa.setTypeEnseignementId(domaine.getTypeEnseignementId());
        jpa.setLibelle(domaine.getLibelle());
        jpa.setEtat(domaine.getEtat());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setDateModification(domaine.getDateModification());
        jpa.setCreePar(domaine.getCreePar());
        jpa.setModifiePar(domaine.getModifiePar());
        return jpa;
    }

    public Branche toDomain(BrancheJpaEntity jpa) {
        return Branche.reconstituer(
            jpa.getId(), jpa.getEtablissementId(), jpa.getSousSystemeId(), jpa.getOrdreEnseignementId(),
            jpa.getTypeEnseignementId(), jpa.getLibelle(), jpa.getEtat(), jpa.getDateCreation(),
            jpa.getDateModification(), jpa.getCreePar(), jpa.getModifiePar()
        );
    }

    public BrancheResponse toResponse(Branche branche) {
        BrancheResponse reponse = new BrancheResponse();
        reponse.setId(branche.getId());
        reponse.setEtablissementId(branche.getEtablissementId());
        reponse.setSousSystemeId(branche.getSousSystemeId());
        reponse.setOrdreEnseignementId(branche.getOrdreEnseignementId());
        reponse.setTypeEnseignementId(branche.getTypeEnseignementId());
        reponse.setLibelle(branche.getLibelle());
        reponse.setEtat(branche.getEtat().name());
        reponse.setDateCreation(branche.getDateCreation());
        reponse.setDateModification(branche.getDateModification());
        return reponse;
    }
}
