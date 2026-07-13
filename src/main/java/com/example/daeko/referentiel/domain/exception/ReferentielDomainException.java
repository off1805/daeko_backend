package com.example.daeko.referentiel.domain.exception;

/**
 * Classe de base de toutes les exceptions métier du module Référentiel Éducatif.
 */
public abstract class ReferentielDomainException extends RuntimeException {

    private final String code;

    protected ReferentielDomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
