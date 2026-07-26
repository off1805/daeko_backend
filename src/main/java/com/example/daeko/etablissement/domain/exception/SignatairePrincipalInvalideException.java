package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-008 — Tentative d'avoir deux signataires principaux actifs simultanément.
 */
public class SignatairePrincipalInvalideException extends EtablissementDomainException {

    private static final String CODE = "ETB-008";
    private static final String MESSAGE_DEFAUT =
            "Un établissement ne peut avoir qu'un seul signataire principal actif à la fois.";

    public SignatairePrincipalInvalideException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public SignatairePrincipalInvalideException(String message) {
        super(CODE, message);
    }
}