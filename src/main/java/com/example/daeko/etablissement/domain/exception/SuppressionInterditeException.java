package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-011 — Tentative de suppression physique d'un établissement.
 */
public class SuppressionInterditeException extends EtablissementDomainException {

    private static final String CODE = "ETB-011";
    private static final String MESSAGE_DEFAUT =
            "La suppression physique d'un établissement est strictement interdite.";

    public SuppressionInterditeException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public SuppressionInterditeException(String message) {
        super(CODE, message);
    }
}