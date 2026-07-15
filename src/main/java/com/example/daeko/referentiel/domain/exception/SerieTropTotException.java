package com.example.daeko.referentiel.domain.exception;

/**
 * REF-003 — Le niveau cible est antérieur au niveau d'apparition de la série,
 * ce qui violerait la règle de positionnement global des séries.
 */
public class SerieTropTotException extends ReferentielDomainException {

    private static final String CODE = "REF-003";
    private static final String MESSAGE_DEFAUT = "Le niveau cible est antérieur au niveau d'apparition de la série.";

    public SerieTropTotException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public SerieTropTotException(String message) {
        super(CODE, message);
    }
}
