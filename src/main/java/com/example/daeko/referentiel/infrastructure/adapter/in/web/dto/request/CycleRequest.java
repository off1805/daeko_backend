package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import java.time.LocalDate;
import java.util.UUID;

public record CycleRequest(
        UUID sousSystemeId,
        UUID ordreEnseignementId,
        String code,
        String libelle,
        String libelleEn,
        Integer rang,
        Integer dureeTheoriqueAnnees,
        String description,
        LocalDate dateEntreeVigueur,
        UUID utilisateurId
) {

    /**
     * Convertit la requête web en commande de création.
     */
    public CreerCycleCommand toCreerCommand() {
        return new CreerCycleCommand(
                this.sousSystemeId,
                this.ordreEnseignementId,
                this.code,
                this.libelle,
                this.libelleEn,
                this.rang,
                this.dureeTheoriqueAnnees,
                this.description,
                this.dateEntreeVigueur,
                this.utilisateurId
        );
    }

    /**
     * Convertit la requête web en commande de modification.
     * Note : Ajuste l'ordre des paramètres si le constructeur de ModifierCycleCommand
     * place l'identifiant (id) à la fin plutôt qu'au début.
     */
    public ModifierCycleCommand toModifierCommand(UUID id) {
        return new ModifierCycleCommand(
                id,
                this.code,
                this.libelle,
                this.libelleEn,
                this.rang
        );
    }
}