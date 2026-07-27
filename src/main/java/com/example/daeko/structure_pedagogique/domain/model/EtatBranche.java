package com.example.daeko.structure_pedagogique.domain.model;

/**
 * Etats du cycle de vie d'une Branche (section 4.5 du dossier d'implementation).
 *
 * Transitions autorisees :
 *   (creation)        -> EN_CONFIGURATION
 *   EN_CONFIGURATION  -> ACTIVE
 *   ACTIVE            -> SUSPENDUE
 *   SUSPENDUE         -> ACTIVE
 *   ACTIVE, SUSPENDUE -> ARCHIVEE
 *
 * Toute autre transition doit etre rejetee avec le code SP-013.
 */
public enum EtatBranche {
    EN_CONFIGURATION,
    ACTIVE,
    SUSPENDUE,
    ARCHIVEE
}
