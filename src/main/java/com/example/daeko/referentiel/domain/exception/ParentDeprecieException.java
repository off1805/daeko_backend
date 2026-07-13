package com.example.daeko.referentiel.domain.exception;

/**
 * REF-006 — Tentative de création d'une entrée dont la référence parente
 * est déjà dépréciée.
 */
public class ParentDeprecieException extends ReferentielDomainException {

    private static final String CODE = "REF-006";
    private static final String MESSAGE_DEFAUT = "Impossible de créer cette entrée : la référence parente est dépréciée.";

    public ParentDeprecieException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public ParentDeprecieException(String message) {
        super(CODE, message);
    }
}
