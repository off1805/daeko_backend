package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.model.EntiteReferentiel;

public abstract class AbstractReferentielApplicationService<T extends EntiteReferentiel> {

    protected final AuditPort auditPort;
    protected final EvenementPublisherPort evenementPublisher;

    protected AbstractReferentielApplicationService(AuditPort auditPort,
                                                    EvenementPublisherPort evenementPublisher) {
        this.auditPort = auditPort;
        this.evenementPublisher = evenementPublisher;
    }

    protected final T executerDepreciation(T entite, String typeEntite, DeprecierCommand command) {
        T avant = copier(entite);
        entite.deprecier(command.getMotif(), command.getDateEffet());
        entite.setModifiePar(command.getUtilisateurId());
        auditPort.enregistrer(typeEntite, entite.getId(), "DEPRECIATION",
                command.getUtilisateurId(), command.getMotif(), avant, entite);
        evenementPublisher.publierDepreciation(typeEntite, entite.getId(),
                getCode(entite), command.getDateEffet(), command.getMotif());
        return entite;
    }

    protected final T executerReactivation(T entite, String typeEntite, ReactiverCommand command) {
        T avant = copier(entite);
        entite.reactiver();
        entite.setModifiePar(command.getUtilisateurId());
        auditPort.enregistrer(typeEntite, entite.getId(), "REACTIVATION",
                command.getUtilisateurId(), null, avant, entite);
        evenementPublisher.publierReactivation(typeEntite, entite.getId(), getCode(entite));
        return entite;
    }

    protected abstract T copier(T source);

    /**
     * Retourne le code métier de l'entité pour l'événement de dépréciation.
     * Peut être surchargé si l'entité n'expose pas getCode() directement.
     */
    protected String getCode(T entite) {
        try {
            return (String) entite.getClass().getMethod("getCode").invoke(entite);
        } catch (Exception e) {
            return entite.getId().toString();
        }
    }
}