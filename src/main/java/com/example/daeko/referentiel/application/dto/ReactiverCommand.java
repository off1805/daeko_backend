package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ReactiverCommand {

    private final UUID entiteId;
    private final UUID utilisateurId;

    public ReactiverCommand(UUID entiteId, UUID utilisateurId) {
        this.entiteId = entiteId;
        this.utilisateurId = utilisateurId;
    }

    public UUID getEntiteId() { return entiteId; }
    public UUID getUtilisateurId() { return utilisateurId; }
}