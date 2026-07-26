package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-007 — Fichier invalide : format non supporté, poids ou dimensions hors limites.
 */
public class FichierInvalideException extends EtablissementDomainException {

    private static final String CODE = "ETB-007";
    private static final String MESSAGE_DEFAUT =
            "Le fichier fourni ne respecte pas les contraintes de format, poids ou dimensions.";

    public FichierInvalideException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public FichierInvalideException(String message) {
        super(CODE, message);
    }
}