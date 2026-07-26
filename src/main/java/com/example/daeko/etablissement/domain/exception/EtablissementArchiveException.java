package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-013 — Établissement ARCHIVÉ : toute modification est refusée.
 */
public class EtablissementArchiveException extends EtablissementDomainException {

    private static final String CODE = "ETB-013";
    private static final String MESSAGE_DEFAUT =
            "L'établissement est archivé et ne peut plus subir aucune modification.";

    public EtablissementArchiveException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public EtablissementArchiveException(String message) {
        super(CODE, message);
    }
}