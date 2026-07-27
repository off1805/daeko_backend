package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.MatiereLocale;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.MatiereLocaleResponse;
import com.example.daeko.structure_pedagogique.infrastructure.entity.MatiereLocaleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MatiereLocaleMapper {

    public MatiereLocaleJpaEntity toJpa(MatiereLocale domaine) {
        MatiereLocaleJpaEntity jpa = new MatiereLocaleJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setBrancheId(domaine.getBrancheId());
        jpa.setCode(domaine.getCode());
        jpa.setLibelle(domaine.getLibelle());
        jpa.setLibelleCourt(domaine.getLibelleCourt());
        jpa.setLibelleEn(domaine.getLibelleEn());
        jpa.setDomaine(domaine.getDomaine());
        jpa.setTypeMatiere(domaine.getTypeMatiere());
        jpa.setBaremeParDefaut((short) domaine.getBaremeParDefaut());
        jpa.setEtat(domaine.getEtat());
        jpa.setMotifDepreciation(domaine.getMotifDepreciation());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setDateModification(domaine.getDateModification());
        jpa.setCreePar(domaine.getCreePar());
        jpa.setModifiePar(domaine.getModifiePar());
        return jpa;
    }

    public MatiereLocale toDomain(MatiereLocaleJpaEntity jpa) {
        return MatiereLocale.reconstituer(
            jpa.getId(), jpa.getBrancheId(), jpa.getCode(), jpa.getLibelle(), jpa.getLibelleCourt(),
            jpa.getLibelleEn(), jpa.getDomaine(), jpa.getTypeMatiere(), jpa.getBaremeParDefaut(),
            jpa.getEtat(), jpa.getMotifDepreciation(), jpa.getDateCreation(), jpa.getDateModification(),
            jpa.getCreePar(), jpa.getModifiePar()
        );
    }

    public MatiereLocaleResponse toResponse(MatiereLocale matiere) {
        MatiereLocaleResponse reponse = new MatiereLocaleResponse();
        reponse.setId(matiere.getId());
        reponse.setBrancheId(matiere.getBrancheId());
        reponse.setCode(matiere.getCode());
        reponse.setLibelle(matiere.getLibelle());
        reponse.setLibelleCourt(matiere.getLibelleCourt());
        reponse.setLibelleEn(matiere.getLibelleEn());
        reponse.setDomaine(matiere.getDomaine());
        reponse.setTypeMatiere(matiere.getTypeMatiere());
        reponse.setBaremeParDefaut(matiere.getBaremeParDefaut());
        reponse.setEtat(matiere.getEtat().name());
        reponse.setMotifDepreciation(matiere.getMotifDepreciation());
        return reponse;
    }
}
