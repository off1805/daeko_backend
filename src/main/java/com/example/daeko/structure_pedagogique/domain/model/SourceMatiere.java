package com.example.daeko.structure_pedagogique.domain.model;

/**
 * Source polymorphe d'une MatiereActive : soit une matiere du referentiel
 * national, soit une matiere locale propre a la branche. Jamais les deux
 * (contrainte SQL num_nonnulls = 1, doublee par la validation applicative SP-008).
 *
 * Valeur derivee : jamais persistee telle quelle, calculee depuis
 * MatiereActive.matiereReferentielId / matiereLocaleId.
 */
public enum SourceMatiere {
    REFERENTIEL,
    LOCALE
}
