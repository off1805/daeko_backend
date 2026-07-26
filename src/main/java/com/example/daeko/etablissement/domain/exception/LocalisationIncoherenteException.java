package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-010 — Localisation incohérente : hiérarchie Région -> Département -> Arrondissement invalide.
 */
public class LocalisationIncoherenteException extends EtablissementDomainException {

    private static final String CODE = "ETB-010";
    private static final String MESSAGE_DEFAUT =
            "La localisation géographique choisie est incohérente dans la hiérarchie administrative.";

    public LocalisationIncoherenteException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public LocalisationIncoherenteException(String message) {
        super(CODE, message);
    }
}