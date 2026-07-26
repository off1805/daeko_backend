package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-015 — Cible en localisation dépréciée (DEPRECATED) lors d'une création.
 */
public class ReferenceGeoDeprecieeException extends EtablissementDomainException {

    private static final String CODE = "ETB-015";
    private static final String MESSAGE_DEFAUT =
            "La référence géographique sélectionnée est dépréciée et ne peut pas être utilisée pour une nouvelle création.";

    public ReferenceGeoDeprecieeException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public ReferenceGeoDeprecieeException(String message) {
        super(CODE, message);
    }
}