package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-005 — Établissement introuvable.
 */
public class EtablissementIntrouvableException extends EtablissementDomainException {

    private static final String CODE = "ETB-005";
    private static final String MESSAGE_DEFAUT =
            "L'établissement demandé est introuvable.";

    public EtablissementIntrouvableException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public EtablissementIntrouvableException(String message) {
        super(CODE, message);
    }
}