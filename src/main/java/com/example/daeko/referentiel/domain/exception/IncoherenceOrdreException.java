package com.example.daeko.referentiel.domain.exception;

/**
 * REF-004 — La filière de la série et le cycle du niveau cible ne portent pas
 * le même ordre d'enseignement, rendant l'association structurellement invalide.
 */
public class IncoherenceOrdreException extends ReferentielDomainException {

    private static final String CODE = "REF-004";
    private static final String MESSAGE_DEFAUT = "La filière de la série et le cycle du niveau cible ne portent pas le même ordre d'enseignement.";

    public IncoherenceOrdreException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public IncoherenceOrdreException(String message) {
        super(CODE, message);
    }
}
