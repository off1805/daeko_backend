package com.example.daeko.referentiel.domain.port;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentielNiveau;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Les méthodes existeAvecSerie et existeSansSerie sont séparées en raison de la
 * double contrainte d'unicité partielle sur la table matiere_referentiel_niveau :
 * serie_id étant nullable, deux index partiels PostgreSQL distincts garantissent
 * l'unicité (voir schema.sql).
 */
public interface MatiereReferentielNiveauRepository {
    MatiereReferentielNiveau sauvegarder(MatiereReferentielNiveau mrn);
    Optional<MatiereReferentielNiveau> trouverParId(UUID id);
    List<MatiereReferentielNiveau> rechercher(EtatReferentiel etat);
    /**
     * Recherche les lignes applicables à un niveau et, si serieId est fourni, inclut
     * à la fois les lignes de cette série ET les lignes sans série (serie_id NULL),
     * conformément à la règle de lecture transverse du référentiel (§5.1, TC-19).
     * Si serieId est null, retourne uniquement les lignes sans série pour ce niveau.
     */
    List<MatiereReferentielNiveau> rechercher(UUID niveauId, UUID serieId, EtatReferentiel etat);
    boolean existeAvecSerie(UUID matiereReferentielId, UUID niveauId, UUID serieId);
    boolean existeSansSerie(UUID matiereReferentielId, UUID niveauId);
}
