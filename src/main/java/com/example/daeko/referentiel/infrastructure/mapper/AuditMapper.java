package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.domain.model.AuditEntree;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.AuditResponse;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.ChampModifieResponse;
import com.example.daeko.referentiel.infrastructure.entity.AuditReferentielJpaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuditMapper {

    private final ObjectMapper objectMapper;

    public AuditMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AuditEntree toDomain(AuditReferentielJpaEntity entity) {
        return new AuditEntree(
                entity.getId(), entity.getTypeEntite(), entity.getEntiteId(),
                entity.getOperation(), entity.getUtilisateurId(), entity.getHorodatage(),
                entity.getMotif(), entity.getValeursAvant(), entity.getValeursApres());
    }

    public AuditResponse toResponse(AuditEntree entree) {
        AuditResponse response = new AuditResponse();
        response.setId(entree.getId());
        response.setTypeEntite(entree.getTypeEntite());
        response.setEntiteId(entree.getEntiteId());
        response.setOperation(entree.getOperation().name());
        response.setUtilisateurId(entree.getUtilisateurId());
        response.setHorodatage(entree.getHorodatage());
        response.setMotif(entree.getMotif());

        Map<String, Object> avant = parseJson(entree.getValeursAvant());
        Map<String, Object> apres = parseJson(entree.getValeursApres());
        response.setChampsModifies(calculerDiff(avant, apres));

        return response;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Erreur de désérialisation JSON pour l'audit", e);
        }
    }

    /**
     * Compare champ par champ deux états JSON désérialisés et retourne uniquement
     * ceux qui diffèrent.
     * - CREATION (avant = null) : tous les champs de "après" sont renvoyés comme
     *   nouveaux (valeurAvant = null), pour que la création reste visible dans le diff.
     * - MODIFICATION / DEPRECIATION / REACTIVATION : seuls les champs qui ont
     *   réellement changé apparaissent.
     */
    private List<ChampModifieResponse> calculerDiff(Map<String, Object> avant, Map<String, Object> apres) {
        if (apres == null) {
            return List.of();
        }
        Map<String, Object> etatAvant = avant != null ? avant : Map.of();

        Set<String> cles = new TreeSet<>();
        cles.addAll(etatAvant.keySet());
        cles.addAll(apres.keySet());

        List<ChampModifieResponse> diff = new ArrayList<>();
        for (String cle : cles) {
            Object valeurAvant = etatAvant.get(cle);
            Object valeurApres = apres.get(cle);
            if (!Objects.equals(valeurAvant, valeurApres)) {
                diff.add(new ChampModifieResponse(cle, valeurAvant, valeurApres));
            }
        }
        return diff;
    }
}