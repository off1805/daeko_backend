package com.example.daeko.structure_pedagogique.domain.service;

import com.example.daeko.structure_pedagogique.application.port.out.ReferentielPort;
import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StructureValidationService {

    private final BrancheRepositoryPort brancheRepositoryPort;
    private final ReferentielPort referentielPort;

    public StructureValidationService(BrancheRepositoryPort brancheRepositoryPort, ReferentielPort referentielPort) {
        this.brancheRepositoryPort = brancheRepositoryPort;
        this.referentielPort = referentielPort;
    }

    /** SP-001 : unicite du triplet (sous-systeme, ordre, type) pour l'etablissement. */
    public void validerTripletBranche(UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId,
                                       UUID typeEnseignementId) {
        boolean dejaDeclare = brancheRepositoryPort.existsByTriplet(
            etablissementId, sousSystemeId, ordreEnseignementId, typeEnseignementId);
        if (dejaDeclare) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_001,
                "Une branche existe deja pour ce triplet (sous-systeme, ordre, type) sur cet etablissement."
            );
        }
    }

    /** SP-020 : refuse la creation d'une branche sur une racine referentielle depreciee. */
    public void validerRacinesActives(UUID sousSystemeId, UUID ordreEnseignementId, UUID typeEnseignementId) {
        if (!referentielPort.estActive(sousSystemeId)
            || !referentielPort.estActive(ordreEnseignementId)
            || !referentielPort.estActive(typeEnseignementId)) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_020,
                "Impossible de creer une branche sur une racine (sous-systeme, ordre ou type) depreciee du referentiel."
            );
        }
    }

    /**
     * SP-020 (niveau deprecie), SP-002 (sous-systeme incoherent via le cycle
     * du niveau), SP-003 (ordre incoherent via le cycle du niveau).
     */
    public void validerNiveauActive(Branche branche, UUID niveauId) {
        ReferentielPort.NiveauVue niveau = referentielPort.chargerNiveau(niveauId);
        if (!niveau.isActive()) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_020,
                "Le niveau " + niveauId + " est deprecie dans le referentiel : activation refusee."
            );
        }

        ReferentielPort.CycleVue cycle = referentielPort.chargerCycle(niveau.getCycleId());

        if (!cycle.getSousSystemeId().equals(branche.getSousSystemeId())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_002,
                "Le niveau " + niveauId + " n'appartient pas au sous-systeme de la branche.",
                detailsSousSysteme(branche.getSousSystemeId(), cycle.getSousSystemeId())
            );
        }

        if (!cycle.getOrdreEnseignementId().equals(branche.getOrdreEnseignementId())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_003,
                "Le cycle du niveau " + niveauId + " ne porte pas l'ordre d'enseignement de la branche.",
                detailsOrdre(branche.getOrdreEnseignementId(), cycle.getOrdreEnseignementId())
            );
        }
    }

    /**
     * SP-020 (filiere depreciee), SP-003 (ordre incoherent), SP-004 (type incoherent).
     */
    public void validerFiliereActive(Branche branche, UUID filiereId) {
        ReferentielPort.FiliereVue filiere = referentielPort.chargerFiliere(filiereId);
        if (!filiere.isActive()) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_020,
                "La filiere " + filiereId + " est depreciee dans le referentiel : activation refusee."
            );
        }

        if (!filiere.getOrdreEnseignementId().equals(branche.getOrdreEnseignementId())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_003,
                "La filiere " + filiereId + " ne porte pas l'ordre d'enseignement de la branche.",
                detailsOrdre(branche.getOrdreEnseignementId(), filiere.getOrdreEnseignementId())
            );
        }

        if (!filiere.getTypeEnseignementId().equals(branche.getTypeEnseignementId())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_004,
                "La filiere " + filiereId + " ne correspond pas au type d'enseignement de la branche.",
                detailsType(branche.getTypeEnseignementId(), filiere.getTypeEnseignementId())
            );
        }
    }

    /**
     * SP-020 (serie depreciee), SP-006 (filiere de la serie absente des
     * filieres actives de la configuration), SP-005 (niveau anterieur au
     * niveau d'apparition officiel de la serie).
     */
    public void validerSerieActive(NiveauActive niveauActive, UUID serieId, Set<UUID> filiereActiveIds) {
        ReferentielPort.SerieVue serie = referentielPort.chargerSerie(serieId);
        if (!serie.isActive()) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_020,
                "La serie " + serieId + " est depreciee dans le referentiel : activation refusee."
            );
        }

        if (!filiereActiveIds.contains(serie.getFiliereId())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_006,
                "La serie " + serieId + " appartient a une filiere qui n'est pas active dans cette configuration.",
                Map.of("filiereId", serie.getFiliereId().toString())
            );
        }

        int positionNiveauCourant = referentielPort.positionGlobale(niveauActive.getNiveauId());
        int positionNiveauApparition = referentielPort.positionGlobale(serie.getNiveauApparitionId());
        if (positionNiveauCourant < positionNiveauApparition) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_005,
                "La serie " + serieId + " ne peut pas etre activee avant son niveau d'apparition officiel.",
                Map.of(
                    "niveauApparitionId", serie.getNiveauApparitionId().toString(),
                    "niveauCourantId", niveauActive.getNiveauId().toString()
                )
            );
        }
    }

    /** SP-007, SP-002, SP-009, SP-020, SP-010, SP-008 */
    public void validerMatiereActive(UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId,
                                      UUID matiereLocaleId, java.math.BigDecimal coefficient) {
        throw new UnsupportedOperationException("validerMatiereActive : complete au jour 6 (matiere active).");
    }

    /** SP-007, SP-017 */
    public void validerClasse(UUID niveauActiveId, UUID serieActiveId) {
        throw new UnsupportedOperationException("validerClasse : complete au jour 6 (classe).");
    }

    private static Map<String, Object> detailsSousSysteme(UUID attendu, UUID trouve) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("sousSystemeAttenduId", attendu.toString());
        details.put("sousSystemeTrouveId", trouve.toString());
        return details;
    }

    private static Map<String, Object> detailsOrdre(UUID attendu, UUID trouve) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("ordreEnseignementAttenduId", attendu.toString());
        details.put("ordreEnseignementTrouveId", trouve.toString());
        return details;
    }

    private static Map<String, Object> detailsType(UUID attendu, UUID trouve) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("typeEnseignementAttenduId", attendu.toString());
        details.put("typeEnseignementTrouveId", trouve.toString());
        return details;
    }
}

