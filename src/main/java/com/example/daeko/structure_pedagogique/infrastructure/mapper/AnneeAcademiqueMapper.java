package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.AnneeAcademiqueResponse;
import com.example.daeko.structure_pedagogique.infrastructure.entity.AnneeAcademiqueJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AnneeAcademiqueMapper {

    public AnneeAcademiqueJpaEntity toJpa(AnneeAcademique domaine) {
        AnneeAcademiqueJpaEntity jpa = new AnneeAcademiqueJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setEtablissementId(domaine.getEtablissementId());
        jpa.setLibelle(domaine.getLibelle());
        jpa.setDateDebut(domaine.getDateDebut());
        jpa.setDateFin(domaine.getDateFin());
        jpa.setEtat(domaine.getEtat());
        jpa.setDateDemarrage(domaine.getDateDemarrage());
        jpa.setDateCloture(domaine.getDateCloture());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setDateModification(domaine.getDateModification());
        jpa.setCreePar(domaine.getCreePar());
        jpa.setModifiePar(domaine.getModifiePar());
        return jpa;
    }

    public AnneeAcademique toDomain(AnneeAcademiqueJpaEntity jpa) {
        return AnneeAcademique.reconstituer(
            jpa.getId(), jpa.getEtablissementId(), jpa.getLibelle(), jpa.getDateDebut(), jpa.getDateFin(),
            jpa.getEtat(), jpa.getDateDemarrage(), jpa.getDateCloture(), jpa.getDateCreation(),
            jpa.getDateModification(), jpa.getCreePar(), jpa.getModifiePar()
        );
    }

    public AnneeAcademiqueResponse toResponse(AnneeAcademique annee) {
        AnneeAcademiqueResponse reponse = new AnneeAcademiqueResponse();
        reponse.setId(annee.getId());
        reponse.setEtablissementId(annee.getEtablissementId());
        reponse.setLibelle(annee.getLibelle());
        reponse.setDateDebut(annee.getDateDebut());
        reponse.setDateFin(annee.getDateFin());
        reponse.setEtat(annee.getEtat().name());
        reponse.setDateDemarrage(annee.getDateDemarrage());
        reponse.setDateCloture(annee.getDateCloture());
        return reponse;
    }
}
