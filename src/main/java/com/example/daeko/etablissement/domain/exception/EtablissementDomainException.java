package com.example.daeko.etablissement.domain.exception;

public abstract class EtablissementDomainException extends RuntimeException {

    private final String code;

    public EtablissementDomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public EtablissementDomainException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}