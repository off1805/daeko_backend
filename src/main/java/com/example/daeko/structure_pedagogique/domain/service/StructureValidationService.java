package com.example.daeko.structure_pedagogique.domain.service;

import com.example.daeko.structure_pedagogique.application.port.out.ReferentielPort;
import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;

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

    /** SP-020, SP-002, SP-003 */
    public void validerNiveauActive(UUID configurationId, UUID niveauId) {
        throw new UnsupportedOperationException("validerNiveauActive : complete au jour 5 (activations).");
    }

    /** SP-020, SP-003, SP-004 */
    public void validerFiliereActive(UUID configurationId, UUID filiereId) {
        throw new UnsupportedOperationException("validerFiliereActive : complete au jour 5 (activations).");
    }

    /** SP-020, SP-006, SP-005 */
    public void validerSerieActive(UUID niveauActiveId, UUID serieId) {
        throw new UnsupportedOperationException("validerSerieActive : complete au jour 5 (activations).");
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
}
