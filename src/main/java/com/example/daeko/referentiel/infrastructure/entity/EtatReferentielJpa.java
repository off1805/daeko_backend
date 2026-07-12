package com.example.daeko.referentiel.infrastructure.entity;

/**
 * Contrainte CHECK cycle de vie :
 *   (etat = 'ACTIVE' AND date_depreciation IS NULL)
 *   OR (etat = 'DEPRECATED' AND date_depreciation IS NOT NULL AND motif_depreciation IS NOT NULL)
 */
public enum EtatReferentielJpa {
    ACTIVE,
    DEPRECATED
}
