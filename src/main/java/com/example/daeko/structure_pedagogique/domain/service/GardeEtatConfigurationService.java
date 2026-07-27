package com.example.daeko.structure_pedagogique.domain.service;

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
}
