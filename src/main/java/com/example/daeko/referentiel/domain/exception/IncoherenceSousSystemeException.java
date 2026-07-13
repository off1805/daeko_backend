package com.example.daeko.referentiel.domain.exception;

/**
 * REF-002 — La matière et le niveau cible n'appartiennent pas au même sous-système,
 * ce qui rendrait l'association MatiereReferentielNiveau incohérente.
 */
public class IncoherenceSousSystemeException extends ReferentielDomainException {

    private static final String CODE = "REF-002";
    private static final String MESSAGE_DEFAUT = "La matière et le niveau cible n'appartiennent pas au même sous-système.";

    public IncoherenceSousSystemeException() {
        super(CODE, MESSAGE_DEFAUT);
    }

    public IncoherenceSousSystemeException(String message) {
        super(CODE, message);
    }
}
