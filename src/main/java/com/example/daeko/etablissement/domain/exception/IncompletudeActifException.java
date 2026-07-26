package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-012 — Passage à ACTIF refusé en raison d'éléments obligatoires manquants.
 */
public class IncompletudeActifException extends EtablissementDomainException {

    private static final String CODE = "ETB-012";
    private static final String MESSAGE_DEFAUT =
            "L'établissement est incomplet et ne peut pas passer à l'état ACTIF.";

    public IncompletudeActifException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public IncompletudeActifException(String message) {
        super(CODE, message);
    }
}