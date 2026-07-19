package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerMatiereNiveauCommand;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentielNiveau;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerMatiereNiveauRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.MatiereReferentielNiveauResponse;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.MatiereReferentielJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.MatiereReferentielNiveauJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.NiveauJpaEntity;
import com.example.daeko.referentiel.infrastructure.entity.SerieJpaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MatiereReferentielNiveauMapper {

    public MatiereReferentielNiveau toDomain(MatiereReferentielNiveauJpaEntity entity) {
        MatiereReferentielNiveau domain = new MatiereReferentielNiveau(
                entity.getMatiereReferentiel().getId(),
                entity.getNiveau().getId(),
                entity.getSerie() != null ? entity.getSerie().getId() : null,
                entity.isEstObligatoire(),
                entity.getCoefficientSuggere(),
                entity.getSourceCoefficient(),
                entity.getBaremeSpecifique(),
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

    public MatiereReferentielNiveauJpaEntity toEntity(MatiereReferentielNiveau domain) {
        MatiereReferentielNiveauJpaEntity entity = new MatiereReferentielNiveauJpaEntity();
        entity.setId(domain.getId());

        MatiereReferentielJpaEntity matiere = new MatiereReferentielJpaEntity();
        matiere.setId(domain.getMatiereReferentielId());
        entity.setMatiereReferentiel(matiere);

        NiveauJpaEntity niveau = new NiveauJpaEntity();
        niveau.setId(domain.getNiveauId());
        entity.setNiveau(niveau);

        if (domain.getSerieId() != null) {
            SerieJpaEntity serie = new SerieJpaEntity();
            serie.setId(domain.getSerieId());
            entity.setSerie(serie);
        } else {
            entity.setSerie(null);
        }

        entity.setEstObligatoire(domain.isEstObligatoire());
        entity.setCoefficientSuggere(domain.getCoefficientSuggere());
        entity.setSourceCoefficient(domain.getSourceCoefficient());
        entity.setBaremeSpecifique(domain.getBaremeSpecifique());
        entity.setDescription(domain.getDescription());
        entity.setDateEntreeVigueur(domain.getDateEntreeVigueur());
        entity.setDateDepreciation(domain.getDateDepreciation());
        entity.setMotifDepreciation(domain.getMotifDepreciation());
        entity.setEtat(EtatReferentielJpa.valueOf(domain.getEtat().name()));
        entity.setCreePar(domain.getCreePar());
        entity.setModifiePar(domain.getModifiePar());
        return entity;
    }

    public CreerMatiereNiveauCommand toCommand(CreerMatiereNiveauRequest request, UUID utilisateurId) {
        boolean estObligatoire = request.getEstObligatoire() != null ? request.getEstObligatoire() : true;
        return new CreerMatiereNiveauCommand(
                request.getMatiereReferentielId(), request.getNiveauId(), request.getSerieId(),
                estObligatoire, request.getCoefficientSuggere(), request.getSourceCoefficient(),
                request.getBaremeSpecifique(), request.getDescription(),
                java.time.LocalDate.now(), utilisateurId);
    }

    public MatiereReferentielNiveauResponse toResponse(MatiereReferentielNiveau domaine) {
        MatiereReferentielNiveauResponse response = new MatiereReferentielNiveauResponse();
        response.setId(domaine.getId());
        response.setEtat(domaine.getEtat() != null ? domaine.getEtat().name() : EtatReferentiel.ACTIVE.name());
        response.setMatiereReferentielId(domaine.getMatiereReferentielId());
        response.setNiveauId(domaine.getNiveauId());
        response.setSerieId(domaine.getSerieId());
        response.setEstObligatoire(domaine.isEstObligatoire());
        response.setCoefficientSuggere(domaine.getCoefficientSuggere());
        response.setSourceCoefficient(domaine.getSourceCoefficient());
        response.setBaremeSpecifique(domaine.getBaremeSpecifique());
        response.setDescription(domaine.getDescription());
        response.setDateEntreeVigueur(domaine.getDateEntreeVigueur());
        response.setDateDepreciation(domaine.getDateDepreciation());
        return response;
    }
}