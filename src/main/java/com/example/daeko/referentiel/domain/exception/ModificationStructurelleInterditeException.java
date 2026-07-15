package com.example.daeko.referentiel.domain.exception;

/**
 * REF-008 — Tentative de modification d'un champ structurel d'une référence
 * (ex : changer le cycleId d'un Niveau existant), ce qui n'est pas autorisé
 * en flux standard.
 */
public class ModificationStructurelleInterditeException extends ReferentielDomainException {

    private static final String CODE = "REF-008";
    private static final String MESSAGE_DEFAUT = "Cette référence structurelle ne peut pas être modifiée en flux standard.";

    public ModificationStructurelleInterditeException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public ModificationStructurelleInterditeException(String message) {
        super(CODE, message);
    }
}
