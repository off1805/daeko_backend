package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-006 — Motif obligatoire absent pour la suspension ou l'archivage.
 */
public class MotifObligatoireException extends EtablissementDomainException {

    private static final String CODE = "ETB-006";
    private static final String MESSAGE_DEFAUT =
            "Un motif explicite est obligatoire pour effectuer cette opération sur l'établissement.";

    public MotifObligatoireException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public MotifObligatoireException(String message) {
        super(CODE, message);
    }
}