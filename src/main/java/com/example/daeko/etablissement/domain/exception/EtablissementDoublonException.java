package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-001 — Doublon d'établissement : même nom + même arrondissement + même ville.
 */
public class EtablissementDoublonException extends EtablissementDomainException {

    private static final String CODE = "ETB-001";
    private static final String MESSAGE_DEFAUT =
            "Un établissement portant le même nom existe déjà dans cet arrondissement et cette ville.";

    public EtablissementDoublonException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public EtablissementDoublonException(String message) {
        super(CODE, message);
    }
}