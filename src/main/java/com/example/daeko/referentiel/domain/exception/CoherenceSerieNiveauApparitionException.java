package com.example.daeko.referentiel.domain.exception;

/**
 * REF-011 — À la création d'une série, le niveau d'apparition appartient à un cycle
 * dont l'ordre d'enseignement diffère de celui de la filière.
 */
public class CoherenceSerieNiveauApparitionException extends ReferentielDomainException {

    private static final String CODE = "REF-011";
    private static final String MESSAGE_DEFAUT =
            "Le niveau d'apparition de la série appartient à un cycle dont l'ordre "
                    + "d'enseignement diffère de celui de la filière.";

    public CoherenceSerieNiveauApparitionException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public CoherenceSerieNiveauApparitionException(String message) {
        super(CODE, message);
    }
}