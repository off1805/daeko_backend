package com.example.daeko.structure_pedagogique.domain.service;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;

/**
 * Garde centralisee appliquant la matrice des droits d'ecriture selon
 * l'etat de l'annee academique (section 4.6 du dossier d'implementation).
 */
public class GardeEtatConfigurationService {

    /** SP-014 : aucune ecriture sur une configuration SCELLEE, quelle que soit l'operation. */
    public void exigerConfigurationOuverte(ConfigurationBrancheAnnee configuration) {
        configuration.exigerOuverte();
    }

    /**
     * SP-014 : un retrait (filiere/niveau/serie desactivee) n'est plus
     * autorise une fois l'annee EN_COURS -- seuls les ajouts restent
     * possibles a ce stade (matrice 4.6, ligne "filieres/niveaux/series").
     */
    public void exigerRetraitAutorise(AnneeAcademique annee) {
        if (annee.estEnCours() || annee.estClotures()) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_014,
                "Le retrait n'est plus autorise une fois l'annee " + annee.getLibelle()
                    + " EN_COURS ou CLOTUREE : seuls les ajouts restent possibles."
            );
        }
    }
}

