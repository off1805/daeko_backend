package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-014 — Un ADMIN_ETABLISSEMENT tente d'agir sur un établissement autre que le sien.
 */
public class AccesRefuseTenantException extends EtablissementDomainException {

    private static final String CODE = "ETB-014";
    private static final String MESSAGE_DEFAUT =
            "Accès refusé : vous ne pouvez pas effectuer cette opération sur un autre établissement.";

    public AccesRefuseTenantException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public AccesRefuseTenantException(String message) {
        super(CODE, message);
    }
}