package com.example.daeko.referentiel.domain.service;

import com.example.daeko.referentiel.domain.exception.IncoherenceOrdreException;
import com.example.daeko.referentiel.domain.exception.IncoherenceSousSystemeException;
import com.example.daeko.referentiel.domain.exception.ParentDeprecieException;
import com.example.daeko.referentiel.domain.exception.SerieTropTotException;
import com.example.daeko.referentiel.domain.exception.CoherenceSerieNiveauApparitionException;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EntiteReferentiel;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.domain.model.PositionGlobale;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.domain.port.CycleRepository;
import com.example.daeko.referentiel.domain.port.FiliereRepository;
import com.example.daeko.referentiel.domain.port.MatiereReferentielRepository;
import com.example.daeko.referentiel.domain.port.NiveauRepository;

/**
 * Service de domaine central regroupant toutes les règles de validation métier
 * transverses du référentiel éducatif : cohérence de position, contrôle du parent
 * actif, cohérence de création d'une série, cohérence entre une matière et un niveau.
 *
 * Ce service est appelé par les services applicatifs avant toute persistance.
 * Il ne persiste rien lui-même.
 */
public class ReferentielValidationService {

    private final NiveauRepository niveauRepository;
    private final CycleRepository cycleRepository;
    private final FiliereRepository filiereRepository;
    private final MatiereReferentielRepository matiereRepository;

    public ReferentielValidationService(NiveauRepository niveauRepository,
                                        CycleRepository cycleRepository,
                                        FiliereRepository filiereRepository,
                                        MatiereReferentielRepository matiereRepository) {
        this.niveauRepository = niveauRepository;
        this.cycleRepository = cycleRepository;
        this.filiereRepository = filiereRepository;
        this.matiereRepository = matiereRepository;
    }

    /**
     * REF-006 — Vérifie que l'entité parente est active avant toute création d'enfant.
     * Lève ParentDeprecieException si le parent est déprécié.
     */
    public void verifierParentActif(EntiteReferentiel parent) {
        if (parent.getEtat() == EtatReferentiel.DEPRECATED) {
            throw new ParentDeprecieException();
        }
    }

    /**
     * REF-003 — Vérifie que le niveau cible est postérieur ou égal au niveau
     * d'apparition de la série (cohérence de position globale).
     * Lève SerieTropTotException si le niveau cible est antérieur.
     */
    public void verifierPositionSerie(Serie serie, Niveau niveauCible) {
        Niveau niveauApparition = niveauRepository.trouverParId(serie.getNiveauApparitionId())
                .orElseThrow(() -> new IllegalStateException(
                        "Niveau d'apparition introuvable pour la série " + serie.getId()));

        Cycle cycleApparition = cycleRepository.trouverParId(niveauApparition.getCycleId())
                .orElseThrow(() -> new IllegalStateException(
                        "Cycle introuvable pour le niveau d'apparition " + niveauApparition.getId()));

        Cycle cycleCible = cycleRepository.trouverParId(niveauCible.getCycleId())
                .orElseThrow(() -> new IllegalStateException(
                        "Cycle introuvable pour le niveau cible " + niveauCible.getId()));

        PositionGlobale positionApparition = new PositionGlobale(
                cycleApparition.getRang(), niveauApparition.getRangDansCycle());
        PositionGlobale positionCible = new PositionGlobale(
                cycleCible.getRang(), niveauCible.getRangDansCycle());

        if (!positionCible.estEgalOuPosterieurA(positionApparition)) {
            throw new SerieTropTotException();
        }
    }

    /**
     * REF-004 — Vérifie que la filière de la série et le cycle du niveau cible
     * portent le même ordre d'enseignement.
     * Lève IncoherenceOrdreException si les ordres diffèrent.
     */
    public void verifierCoherenceOrdre(Serie serie, Niveau niveauCible) {
        Filiere filiere = filiereRepository.trouverParId(serie.getFiliereId())
                .orElseThrow(() -> new IllegalStateException(
                        "Filière introuvable pour la série " + serie.getId()));

        Cycle cycleCible = cycleRepository.trouverParId(niveauCible.getCycleId())
                .orElseThrow(() -> new IllegalStateException(
                        "Cycle introuvable pour le niveau cible " + niveauCible.getId()));

        if (!filiere.getOrdreEnseignementId().equals(cycleCible.getOrdreEnseignementId())) {
            throw new IncoherenceOrdreException();
        }
    }

    /**
     * REF-002 — Vérifie que la matière et le niveau cible appartiennent au même
     * sous-système éducatif.
     * Lève IncoherenceSousSystemeException si les sous-systèmes diffèrent.
     */
    public void verifierCoherenceSousSysteme(MatiereReferentiel matiere, Niveau niveauCible) {
        Cycle cycleCible = cycleRepository.trouverParId(niveauCible.getCycleId())
                .orElseThrow(() -> new IllegalStateException(
                        "Cycle introuvable pour le niveau cible " + niveauCible.getId()));

        if (!matiere.getSousSystemeId().equals(cycleCible.getSousSystemeId())) {
            throw new IncoherenceSousSystemeException();
        }
    }

    /**
     * Vérifie à la fois REF-003 et REF-004 pour une association MatiereReferentielNiveau
     * avec série : position et cohérence d'ordre.
     */
    public void verifierCoherenceAvecSerie(Serie serie, Niveau niveauCible) {
        verifierPositionSerie(serie, niveauCible);
        verifierCoherenceOrdre(serie, niveauCible);
    }

    /**
     * REF-011 — Vérifie, à la création d'une série, que le niveau d'apparition
     * appartient à un cycle dont l'ordre d'enseignement correspond à celui de la filière.
     * Lève CoherenceSerieNiveauApparitionException si les ordres diffèrent.
     *
     * Cette règle sera utilisée par le futur SerieApplicationService lors de la création
     * d'une nouvelle série (voir 4.2 — fonction validerCreationSerie).
     */
    public void verifierCoherenceCreationSerie(Filiere filiere, Niveau niveauApparition) {
        Cycle cycleApparition = cycleRepository.trouverParId(niveauApparition.getCycleId())
                .orElseThrow(() -> new IllegalStateException(
                        "Cycle introuvable pour le niveau d'apparition " + niveauApparition.getId()));

        if (!cycleApparition.getOrdreEnseignementId().equals(filiere.getOrdreEnseignementId())) {
            throw new CoherenceSerieNiveauApparitionException();
        }
    }
}
