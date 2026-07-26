package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-003 — Rôle insuffisant pour exécuter cette opération sur l'établissement.
 */
public class RoleInsuffisantException extends EtablissementDomainException {

    private static final String CODE = "ETB-003";
    private static final String MESSAGE_DEFAUT =
            "Privilèges insuffisants pour modifier le cycle de vie de cet établissement.";

    public RoleInsuffisantException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public RoleInsuffisantException(String message) {
        super(CODE, message);
    }
}