package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-004 — Transition d'état interdite pour l'établissement.
 */
public class TransitionEtatInterditeException extends EtablissementDomainException {

    private static final String CODE = "ETB-004";
    private static final String MESSAGE_DEFAUT =
            "La transition vers cet état n'est pas autorisée depuis l'état actuel de l'établissement.";

    public TransitionEtatInterditeException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public TransitionEtatInterditeException(String message) {
        super(CODE, message);
    }
}