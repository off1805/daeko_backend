package com.example.daeko.structure_pedagogique.domain.model;

/**
 * Etat d'une ConfigurationBrancheAnnee.
 *
 * OUVERTE : ecritures autorisees selon la matrice des droits (section 4.6).
 * SCELLEE : toute ecriture est refusee (SP-014). Atteint uniquement via la
 *           cloture de l'annee academique associee, jamais directement.
 */
public enum EtatConfiguration {
    OUVERTE,
    SCELLEE
}
