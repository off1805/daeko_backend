package com.example.daeko.referentiel.domain.exception;

/**
 * REF-010 — Aucune entité trouvée pour l'identifiant fourni.
 */
public class EntiteIntrouvableException extends ReferentielDomainException {

    private static final String CODE = "REF-010";
    private static final String MESSAGE_DEFAUT = "Aucune entrée trouvée pour cet identifiant.";

    public EntiteIntrouvableException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public EntiteIntrouvableException(String message) {
        super(CODE, message);
    }
}
