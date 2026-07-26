package com.example.daeko.etablissement.domain.exception;

/**
 * ETB-009 — En-tête BILINGUE dont au moins une ligne n'a pas de texte_en renseigné.
 */
public class EnteteBilingueIncompleteException extends EtablissementDomainException {

    private static final String CODE = "ETB-009";
    private static final String MESSAGE_DEFAUT =
            "En mode BILINGUE, chaque ligne d'en-tête doit impérativement comporter une traduction en anglais.";

    public EnteteBilingueIncompleteException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public EnteteBilingueIncompleteException(String message) {
        super(CODE, message);
    }
}