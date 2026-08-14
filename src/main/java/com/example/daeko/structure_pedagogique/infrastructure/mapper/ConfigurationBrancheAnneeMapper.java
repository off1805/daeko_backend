package com.example.daeko.structure_pedagogique.infrastructure.mapper;

import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.ConfigurationBrancheAnneeResponse;
import com.example.daeko.structure_pedagogique.infrastructure.entity.ConfigurationBrancheAnneeJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationBrancheAnneeMapper {

    public ConfigurationBrancheAnneeJpaEntity toJpa(ConfigurationBrancheAnnee domaine) {
        ConfigurationBrancheAnneeJpaEntity jpa = new ConfigurationBrancheAnneeJpaEntity();
        jpa.setId(domaine.getId());
        jpa.setBrancheId(domaine.getBrancheId());
        jpa.setAnneeAcademiqueId(domaine.getAnneeAcademiqueId());
        jpa.setEtat(domaine.getEtat());
        jpa.setDupliqueeDepuisId(domaine.getDupliqueeDepuisId().orElse(null));
        jpa.setDateScellement(domaine.getDateScellement());
        jpa.setDateCreation(domaine.getDateCreation());
        jpa.setDateModification(domaine.getDateModification());
        jpa.setCreePar(domaine.getCreePar());
        jpa.setModifiePar(domaine.getModifiePar());
        return jpa;
    }

    public ConfigurationBrancheAnnee toDomain(ConfigurationBrancheAnneeJpaEntity jpa) {
        return ConfigurationBrancheAnnee.reconstituer(
            jpa.getId(), jpa.getBrancheId(), jpa.getAnneeAcademiqueId(), jpa.getEtat(),
            jpa.getDupliqueeDepuisId(), jpa.getDateScellement(), jpa.getDateCreation(),
            jpa.getDateModification(), jpa.getCreePar(), jpa.getModifiePar()
        );
    }

    public ConfigurationBrancheAnneeResponse toResponse(ConfigurationBrancheAnnee configuration) {
        ConfigurationBrancheAnneeResponse reponse = new ConfigurationBrancheAnneeResponse();
        reponse.setId(configuration.getId());
        reponse.setBrancheId(configuration.getBrancheId());
        reponse.setAnneeAcademiqueId(configuration.getAnneeAcademiqueId());
        reponse.setEtat(configuration.getEtat().name());
        reponse.setDupliqueeDepuisId(configuration.getDupliqueeDepuisId().orElse(null));
        return reponse;
    }
}
