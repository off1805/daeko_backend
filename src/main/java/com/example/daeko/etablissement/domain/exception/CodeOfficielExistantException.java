package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-002 — Code établissement officiel déjà utilisé.
 */
public class CodeOfficielExistantException extends EtablissementDomainException {

    private static final String CODE = "ETB-002";
    private static final String MESSAGE_DEFAUT =
            "Le code officiel d'établissement renseigné est déjà utilisé.";

    public CodeOfficielExistantException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public CodeOfficielExistantException(String message) {
        super(CODE, message);
    }
}