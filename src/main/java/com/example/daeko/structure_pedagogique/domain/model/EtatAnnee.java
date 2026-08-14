package com.example.daeko.structure_pedagogique.domain.model;

/**
 * Etats du cycle de vie d'une AnneeAcademique (section 4.4 du dossier d'implementation).
 *
 * Transitions autorisees :
 *   (creation)       -> EN_PREPARATION
 *   EN_PREPARATION   -> EN_COURS
 *   EN_COURS         -> CLOTUREE
 *   EN_PREPARATION   -> CLOTUREE   (cas limite : annee abandonnee, jamais demarree)
 *   CLOTUREE         -> (aucune)
 *
 * Toute transition sortante depuis CLOTUREE est rejetee avec le code SP-013.
 */
public enum EtatAnnee {
    EN_PREPARATION,
    EN_COURS,
    CLOTUREE
}
