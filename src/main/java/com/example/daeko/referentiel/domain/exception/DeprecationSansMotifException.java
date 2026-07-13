package com.example.daeko.referentiel.domain.exception;

/**
 * REF-005 — Tentative de dépréciation sans motif fourni.
 * La méthode deprecier() de EntiteReferentiel exige un motif non vide.
 */
public class DeprecationSansMotifException extends ReferentielDomainException {

    private static final String CODE = "REF-005";
    private static final String MESSAGE_DEFAUT = "Un motif est obligatoire pour toute dépréciation.";

    public DeprecationSansMotifException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public DeprecationSansMotifException(String message) {
        super(CODE, message);
    }
}
