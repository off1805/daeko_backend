package com.example.daeko.referentiel.domain.exception;

/**
 * REF-001 — Violation de la contrainte d'unicité d'un code dans son périmètre
 */
public class ViolationUniciteException extends ReferentielDomainException {

    private static final String CODE = "REF-001";
    private static final String MESSAGE_DEFAUT = "Une entrée avec ce code existe déjà dans ce périmètre.";

    public ViolationUniciteException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public ViolationUniciteException(String message) {
        super(CODE, message);
    }
}
