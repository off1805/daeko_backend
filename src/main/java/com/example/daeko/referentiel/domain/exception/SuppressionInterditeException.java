package com.example.daeko.referentiel.domain.exception;

/**
 * REF-007 — Tentative de suppression physique d'une entité du référentiel.
 * Ce module n'autorise que la dépréciation ; la suppression physique est interdite.
 */
public class SuppressionInterditeException extends ReferentielDomainException {

    private static final String CODE = "REF-007";
    private static final String MESSAGE_DEFAUT = "La suppression physique n'est pas autorisée sur ce module ; utiliser la dépréciation.";

    public SuppressionInterditeException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public SuppressionInterditeException(String message) {
        super(CODE, message);
    }
}
